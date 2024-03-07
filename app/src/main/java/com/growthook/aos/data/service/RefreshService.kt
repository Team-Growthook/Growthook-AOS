package com.growthook.aos.data.service

import com.growthook.aos.data.model.remote.response.ResponseRefreshTokenDto
import retrofit2.http.GET

interface RefreshService {

    @GET("api/v1/auth/token")
    suspend fun getRefreshToken(): ResponseRefreshTokenDto
}
