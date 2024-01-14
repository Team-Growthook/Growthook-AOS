package com.growthook.aos.data.service

import com.growthook.aos.data.model.request.RequestSeedMoveDto
import com.growthook.aos.data.model.request.RequestSeedPostDto
import com.growthook.aos.data.model.response.ResponseDataDto
import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.model.response.ResponseGetSeedDto
import com.growthook.aos.data.model.response.ResponseMoveSeedDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SeedService {

    @GET("api/v1/seed/{seedId}/detail")
    suspend fun getSeed(
        @Path("seedId") seedId: Int,
    ): ResponseGetSeedDto

    @DELETE("api/v1/seed/{seedId}")
    suspend fun deleteSeed(
        @Path("seedId") seedId: Int,
    ): ResponseDto

    @POST("api/v1/seed/{seedId}/move")
    suspend fun moveSeed(
        @Path("seedId") seedId: Int,
        @Body request: RequestSeedMoveDto,
    ): ResponseMoveSeedDto

    @POST("api/v1/cave/{caveId}/seed")
    suspend fun postSeed(
        @Path("caveId") caveId: Int,
        @Body request: RequestSeedPostDto,
    ): ResponseDataDto
}
