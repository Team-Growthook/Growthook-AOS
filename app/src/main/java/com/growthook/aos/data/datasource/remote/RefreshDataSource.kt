package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.response.ResponseRefreshTokenDto

interface RefreshDataSource {
    suspend fun getRefreshToken(): ResponseRefreshTokenDto
}
