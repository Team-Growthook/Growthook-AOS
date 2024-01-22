package com.growthook.aos.di

import com.growthook.aos.data.datasource.remote.ActionplanDataSource
import com.growthook.aos.data.datasource.remote.CaveDataSource
import com.growthook.aos.data.datasource.remote.MemberDataSource
import com.growthook.aos.data.datasource.remote.RefreshDataSource
import com.growthook.aos.data.datasource.remote.ReviewDataSource
import com.growthook.aos.data.datasource.remote.SeedDataSource
import com.growthook.aos.data.datasource.remote.SignUpDataSource
import com.growthook.aos.data.datasource.remote.impl.ActionplanDataSourceImpl
import com.growthook.aos.data.datasource.remote.impl.CaveDataSourceImpl
import com.growthook.aos.data.datasource.remote.impl.MemberDataSourceImpl
import com.growthook.aos.data.datasource.remote.impl.RefreshDataSourceImpl
import com.growthook.aos.data.datasource.remote.impl.ReviewDataSourceImpl
import com.growthook.aos.data.datasource.remote.impl.SeedDataSourceImpl
import com.growthook.aos.data.datasource.remote.impl.SignUpDataSourceImpl
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

    @Singleton
    @Binds
    abstract fun providesMemberDataSource(dataSourceImpl: MemberDataSourceImpl): MemberDataSource

    @Singleton
    @Binds
    abstract fun providesSignUpDataSource(dataSourceImpl: SignUpDataSourceImpl): SignUpDataSource

    @Singleton
    @Binds
    abstract fun providesRefreshDataSource(dataSourceImpl: RefreshDataSourceImpl): RefreshDataSource
}
