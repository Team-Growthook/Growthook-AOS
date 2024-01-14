package com.growthook.aos.di

import com.growthook.aos.data.repository.ActionplanRepositoryImpl
import com.growthook.aos.data.repository.CaveRepositoryImpl
import com.growthook.aos.data.repository.ReviewRepositoryImpl
import com.growthook.aos.data.repository.SeedRepositoryImpl
import com.growthook.aos.data.repository.TokenRepositoryImpl
import com.growthook.aos.data.repository.UserRepositoryImpl
import com.growthook.aos.domain.repository.ActionplanRepository
import com.growthook.aos.domain.repository.CaveRepository
import com.growthook.aos.domain.repository.ReviewRepository
import com.growthook.aos.domain.repository.SeedRepository
import com.growthook.aos.domain.repository.TokenRepository
import com.growthook.aos.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun providesTokenRepository(repoImpl: TokenRepositoryImpl): TokenRepository

    @Singleton
    @Binds
    abstract fun providesUserRepository(repoImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun providesCaveRepository(repoImpl: CaveRepositoryImpl): CaveRepository

    @Singleton
    @Binds
    abstract fun providesSeedRepository(repoImpl: SeedRepositoryImpl): SeedRepository

    @Singleton
    @Binds
    abstract fun providesActionplanRepository(repoImpl: ActionplanRepositoryImpl): ActionplanRepository

    @Singleton
    @Binds
    abstract fun providesReviewRepository(repoImpl: ReviewRepositoryImpl): ReviewRepository
}
