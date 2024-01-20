package com.growthook.aos.di

import com.growthook.aos.data.service.ActionplanService
import com.growthook.aos.data.service.SignUpService
import com.growthook.aos.data.service.CaveService
import com.growthook.aos.data.service.MemberService
import com.growthook.aos.data.service.ReviewService
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

    @Provides
    @Singleton
    fun provideActionplanService(@GrowthookRetrofit retrofit: Retrofit): ActionplanService =
        retrofit.create(ActionplanService::class.java)

    @Provides
    @Singleton
    fun provideMemberService(@GrowthookRetrofit retrofit: Retrofit): MemberService =
        retrofit.create(MemberService::class.java)

    @Provides
    @Singleton
    fun provideReviewService(@GrowthookRetrofit retrofit: Retrofit): ReviewService =
        retrofit.create(ReviewService::class.java)

    @Provides
    @Singleton
    fun provideSignUpService(@SignUpRetrofit retrofit: Retrofit): SignUpService =
        retrofit.create(SignUpService::class.java)
}
