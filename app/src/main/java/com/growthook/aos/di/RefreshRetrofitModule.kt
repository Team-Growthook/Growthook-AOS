package com.growthook.aos.di

import com.growthook.aos.BuildConfig
import com.growthook.aos.domain.repository.TokenRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RefreshRetrofitModule {
    private const val PUZZLING_BASE_URL = BuildConfig.GROWTHOOK_BASE_URL

    private const val BEARER = "Bearer "
    private const val AUTHORIZATION = "Authorization"
    private const val REFRESH_HEADER = "refreshToken"

    @Provides
    @Singleton
    @RefreshRetrofit
    fun provideLoginOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @RefreshRetrofit tokenInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .build()

    @Singleton
    @Provides
    @RefreshRetrofit
    fun provideLoginRetrofit(@RefreshRetrofit okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(PUZZLING_BASE_URL)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    @RefreshRetrofit
    fun refreshTokenInterceptor(tokenRepository: TokenRepository): Interceptor {
        val requestInterceptor = Interceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder()
            val token = runBlocking {
                tokenRepository.getToken()
            }

            builder.addHeader(
                AUTHORIZATION,
                BEARER + token.accessToken,
            ).addHeader(
                REFRESH_HEADER,
                BEARER + token.refreshToken,
            )
            chain.proceed(builder.build())
        }
        return requestInterceptor
    }
}
