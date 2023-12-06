package com.growthook.aos.data.repository

import androidx.datastore.core.DataStore
import com.growthook.aos.data.entity.Token
import com.growthook.aos.domain.repository.TokenRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(private val dataStore: DataStore<Token>) :
    TokenRepository {
    override suspend fun setToken(accessToken: String) {
        dataStore.updateData { Token(accessToken) }
    }

    override suspend fun getToken(): Token = dataStore.data.first()
}
