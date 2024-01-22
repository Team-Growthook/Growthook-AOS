package com.growthook.aos.domain.repository

import com.growthook.aos.data.entity.Token

interface RefreshRepository {

    suspend fun getRefreshToken(): Result<Token>
}
