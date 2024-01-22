package com.growthook.aos.di

import android.content.Context
import android.util.Log
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.growthook.aos.domain.repository.RefreshRepository
import com.growthook.aos.domain.repository.TokenRepository
import com.growthook.aos.domain.repository.UserRepository
import com.growthook.aos.presentation.onboarding.LoginActivity
import com.growthook.aos.util.extension.isJsonArray
import com.growthook.aos.util.extension.isJsonObject
import com.jakewharton.processphoenix.ProcessPhoenix
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val GROWTHOOK_BASE_URL = com.growthook.aos.BuildConfig.GROWTHOOK_BASE_URL

    private const val BAD_REQUEST = 400
    private const val EXPIRED_TOKEN = 401

    private const val BEARER = "Bearer "
    private const val AUTHORIZATION = "Authorization"

    @Provides
    @Singleton
    @GrowthookRetrofit
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        networkFlipperPlugin: NetworkFlipperPlugin,
        @GrowthookRetrofit tokenInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            addInterceptor(tokenInterceptor)
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
    fun provideGrowthookRetrofit(@GrowthookRetrofit okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(GROWTHOOK_BASE_URL)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    @GrowthookRetrofit
    fun tokenInterceptor(
        @ApplicationContext context: Context,
        tokenRepository: TokenRepository,
        refreshRepository: RefreshRepository,
        userRepository: UserRepository,
    ): Interceptor {
        val requestInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val builder = originalRequest.newBuilder()
            val accessToken = runBlocking {
                tokenRepository.getToken().accessToken
            }
            builder.addHeader(
                AUTHORIZATION,
                BEARER + accessToken,
            )
            var response = chain.proceed(builder.build())

            when (response.code) {
                BAD_REQUEST, EXPIRED_TOKEN -> {
                    runBlocking {
                        refreshRepository.getRefreshToken().onSuccess { token ->
                            response = chain.proceed(
                                originalRequest.newBuilder()
                                    .addHeader(AUTHORIZATION, BEARER + token.accessToken)
                                    .build(),
                            )
                        }.onFailure { throwable ->
                            Timber.e(throwable.message)
                            if (throwable is HttpException && (response.code == BAD_REQUEST || response.code == EXPIRED_TOKEN)) {
                                tokenRepository.setToken("", "")
                                userRepository.setUserInfo("", 0, true)
                                ProcessPhoenix.triggerRebirth(
                                    context,
                                    LoginActivity.newInstance(context),
                                )
                            }
                        }
                    }
                }
            }
            response
        }
        return requestInterceptor
    }
}
