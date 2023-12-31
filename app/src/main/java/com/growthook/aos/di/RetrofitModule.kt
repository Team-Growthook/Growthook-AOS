package com.growthook.aos.di

import android.util.Log
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.growthook.aos.util.extension.isJsonArray
import com.growthook.aos.util.extension.isJsonObject
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val GROWTHOOK_BASE_URL = com.growthook.aos.BuildConfig.GROWTHOOK_BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        networkFlipperPlugin: NetworkFlipperPlugin,
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            addNetworkInterceptor(FlipperOkhttpInterceptor(networkFlipperPlugin))
        }.build()

    @Singleton
    @Provides
    fun provideNetworkFlipper(): NetworkFlipperPlugin {
        return NetworkFlipperPlugin()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            when {
                message.isJsonObject() ->
                    Log.d("retrofit", JSONObject(message).toString(4))

                message.isJsonArray() ->
                    Log.d("retrofit", JSONArray(message).toString(4))

                else -> {
                    Log.d("retrofit", "CONNECTION INFO -> $message")
                }
            }
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Singleton
    @Provides
    @GrowthookRetrofit
    fun provideGrowthookRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(GROWTHOOK_BASE_URL)
        .client(okHttpClient)
        .build()
}
