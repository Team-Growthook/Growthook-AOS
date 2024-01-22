package com.growthook.aos.domain.repository

import com.growthook.aos.data.entity.Token

interface TokenRepository {
    suspend fun setToken(accessToken: String, refreshToken: String)

    suspend fun getToken(): Token
}
