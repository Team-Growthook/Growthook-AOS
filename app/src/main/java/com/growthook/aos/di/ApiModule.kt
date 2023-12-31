package com.growthook.aos.di

import com.growthook.aos.data.service.CaveService
import com.growthook.aos.data.service.SeedService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideCaveService(@GrowthookRetrofit retrofit: Retrofit): CaveService =
        retrofit.create(CaveService::class.java)

    @Provides
    @Singleton
    fun provideSeedService(@GrowthookRetrofit retrofit: Retrofit): SeedService =
        retrofit.create(SeedService::class.java)
}
