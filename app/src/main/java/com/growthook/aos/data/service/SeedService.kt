package com.growthook.aos.data.service

import com.growthook.aos.data.model.request.RequestSeedMoveDto
import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.model.response.ResponseMoveSeedDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface SeedService {

    @DELETE("api/v1/seed/{seedId}")
    suspend fun deleteSeed(
        @Path("seedId") seedId: Int,
    ): ResponseDto

    @POST("api/v1/seed/{seedId}/move")
    suspend fun moveSeed(
        @Path("seedId") seedId: Int,
        @Body request: RequestSeedMoveDto,
    ): ResponseMoveSeedDto
}
