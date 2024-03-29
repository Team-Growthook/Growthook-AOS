package com.growthook.aos.data.service

import com.growthook.aos.data.model.remote.request.RequestSeedModifyDto
import com.growthook.aos.data.model.remote.request.RequestSeedMoveDto
import com.growthook.aos.data.model.remote.request.RequestSeedPostDto
import com.growthook.aos.data.model.remote.response.ResponseAlarmDto
import com.growthook.aos.data.model.remote.response.ResponseDto
import com.growthook.aos.data.model.remote.response.ResponseGetCaveSeedsDto
import com.growthook.aos.data.model.remote.response.ResponseGetSeedDto
import com.growthook.aos.data.model.remote.response.ResponseGetSeedsDto
import com.growthook.aos.data.model.remote.response.ResponseMoveSeedDto
import com.growthook.aos.data.model.remote.response.ResponsePostSeedDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
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
    ): ResponsePostSeedDto

    @GET("api/v1/cave/{caveId}/seed/list")
    suspend fun getCaveSeeds(
        @Path("caveId") caveId: Int,
    ): ResponseGetCaveSeedsDto

    @GET("api/v1/member/{memberId}/alarm")
    suspend fun getSeedAlarm(
        @Path("memberId") memberInt: Int,
    ): ResponseAlarmDto

    @GET("api/v1/member/{memberId}/seed/list")
    suspend fun getSeeds(
        @Path("memberId") memberInt: Int,
    ): ResponseGetSeedsDto

    @PATCH("api/v1/seed/{seedId}/lock/status")
    suspend fun unLockSeed(
        @Path("seedId") seedId: Int,
    ): ResponseDto

    @PATCH("api/v1/seed/{seedId}/scrap/status")
    suspend fun scrapSeed(
        @Path("seedId") seedId: Int,
    ): ResponseDto

    @PATCH("api/v1/seed/{seedId}")
    suspend fun modifySeed(
        @Path("seedId") seedId: Int,
        @Body request: RequestSeedModifyDto,
    ): ResponseDto
}
