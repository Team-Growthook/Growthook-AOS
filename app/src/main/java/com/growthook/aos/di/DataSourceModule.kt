package com.growthook.aos.di

import com.growthook.aos.data.datasource.remote.ActionplanDataSource
import com.growthook.aos.data.datasource.remote.CaveDataSource
import com.growthook.aos.data.datasource.remote.ReviewDataSource
import com.growthook.aos.data.datasource.remote.SeedDataSource
import com.growthook.aos.data.datasource.remote.impl.ActionplanDataSourceImpl
import com.growthook.aos.data.datasource.remote.impl.CaveDataSourceImpl
import com.growthook.aos.data.datasource.remote.impl.ReviewDataSourceImpl
import com.growthook.aos.data.datasource.remote.impl.SeedDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun providesCaveDataSource(dataSourceImpl: CaveDataSourceImpl): CaveDataSource

    @Singleton
    @Binds
    abstract fun providesSeedDataSource(dataSourceImpl: SeedDataSourceImpl): SeedDataSource

    @Singleton
    @Binds
    abstract fun providesActionplanDataSource(dataSourceImpl: ActionplanDataSourceImpl): ActionplanDataSource

    @Singleton
    @Binds
    abstract fun providesReviewDataSource(dataSourceImpl: ReviewDataSourceImpl): ReviewDataSource
}
