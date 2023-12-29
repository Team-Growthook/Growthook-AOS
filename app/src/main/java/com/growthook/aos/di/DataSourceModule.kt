package com.growthook.aos.di

import com.growthook.aos.data.datasource.remote.CaveDataSource
import com.growthook.aos.data.datasource.remote.impl.CaveDataSourceImpl
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
}
