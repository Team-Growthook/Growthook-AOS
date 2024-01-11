package com.growthook.aos.data.service

import com.growthook.aos.data.model.request.RequestSeedMoveDto
import com.growthook.aos.data.model.request.RequestSeedPostDto
import com.growthook.aos.data.model.response.ResponseAlarmDto
import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.model.response.ResponseGetCaveSeedsDto
import com.growthook.aos.data.model.response.ResponseMoveSeedDto
import com.growthook.aos.data.model.response.ResponsePostSeedDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
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

    @POST("api/v1/cave/{caveId}/seed")
    suspend fun postSeed(
        @Path("caveId") caveId: Int,
        @Body request: RequestSeedPostDto,
    ): ResponsePostSeedDto

    @GET("api/v1/cave/{caveId}/seed/list")
    suspend fun getCaveSeeds(
        @Path("caveId") caveId: Int,
    ): ResponseGetCaveSeedsDto

    @GET("api/v1/member/{memberId}/alarm")
    suspend fun getSeedAlarm(
        @Path("memberId") memberInt: Int,
    ): ResponseAlarmDto
}
