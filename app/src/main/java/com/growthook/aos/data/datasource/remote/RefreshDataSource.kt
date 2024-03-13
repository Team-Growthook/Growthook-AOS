package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.remote.response.ResponseRefreshTokenDto

interface RefreshDataSource {
    suspend fun getRefreshToken(): ResponseRefreshTokenDto
}
