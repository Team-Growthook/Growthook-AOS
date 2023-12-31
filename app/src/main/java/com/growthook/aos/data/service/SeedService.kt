package com.growthook.aos.data.service

import com.growthook.aos.data.model.response.ResponseDto
import retrofit2.http.DELETE
import retrofit2.http.Path

interface SeedService {

    @DELETE("api/v1/seed/@{seedId}")
    suspend fun deleteSeed(
        @Path("seedId") seedId: Int,
    ): ResponseDto
}
