package com.growthook.aos.di

import com.growthook.aos.data.service.CaveService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@Singleton
object ApiModule {

    @Provides
    @Singleton
    fun provideCaveService(@GrowthookRetrofit retrofit: Retrofit): CaveService =
        retrofit.create(CaveService::class.java)
}
