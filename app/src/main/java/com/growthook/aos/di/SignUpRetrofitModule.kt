package com.growthook.aos.di

import com.growthook.aos.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SignUpRetrofitModule {
    private const val PUZZLING_BASE_URL = BuildConfig.GROWTHOOK_BASE_URL

    @Provides
    @Singleton
    @SignUpRetrofit
    fun provideLoginOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @SignUpRetrofit tokenInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .build()

    @Singleton
    @Provides
    @SignUpRetrofit
    fun provideLoginRetrofit(@SignUpRetrofit okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(PUZZLING_BASE_URL)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    @SignUpRetrofit
    fun loginInterceptor(): Interceptor {
        val requestInterceptor = Interceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder()
            chain.proceed(builder.build())
        }
        return requestInterceptor
    }
}