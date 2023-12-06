package com.growthook.aos.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.growthook.aos.data.datasource.local.TokenDataSource
import com.growthook.aos.data.datasource.local.UserDataSource
import com.growthook.aos.data.entity.Token
import com.growthook.aos.data.entity.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private const val USER_DATA_STORE_FILE_NAME = "user_prefs.pb"
    private const val TOKEN_DATA_STORE_FILE_NAME = "token_prefs.pb"

    @Provides
    @Singleton
    fun provideTokenDataStore(
        @ApplicationContext appContext: Context,
        tokenDataSource: TokenDataSource,
    ): DataStore<Token> {
        return DataStoreFactory.create(
            serializer = tokenDataSource,
            produceFile = {
                appContext.dataStoreFile(TOKEN_DATA_STORE_FILE_NAME)
            },
        )
    }

    @Provides
    @Singleton
    fun provideUserDataStore(
        @ApplicationContext appContext: Context,
        userDataSource: UserDataSource,
    ): DataStore<User> {
        return DataStoreFactory.create(
            serializer = userDataSource,
            produceFile = {
                appContext.dataStoreFile(USER_DATA_STORE_FILE_NAME)
            },
        )
    }
}
