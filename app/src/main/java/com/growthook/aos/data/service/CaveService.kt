package com.growthook.aos.data.service

import com.growthook.aos.data.model.remote.request.RequestCaveModifyDto
import com.growthook.aos.data.model.remote.request.RequestCavePostDto
import com.growthook.aos.data.model.remote.response.ResponseDto
import com.growthook.aos.data.model.remote.response.ResponseGetCavesDto
import com.growthook.aos.data.model.remote.response.ResponseGetDetailCaveDto
import com.growthook.aos.data.model.remote.response.ResponsePostCaveDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CaveService {

    @DELETE("api/v1/cave/{caveId}")
    suspend fun deleteCave(
        @Path("caveId") caveId: Int,
    ): ResponseDto

    @GET("api/v1/member/{memberId}/cave/all")
    suspend fun getCaves(
        @Path("memberId") memberId: Int,
    ): ResponseGetCavesDto

    @GET("api/v1/member/{memberId}/cave/{caveId}/detail")
    suspend fun getCaveDetail(
        @Path("memberId") memberId: Int,
        @Path("caveId") caveId: Int,
    ): ResponseGetDetailCaveDto

    @PATCH("api/v1/cave/{caveId}")
    suspend fun modifyCave(
        @Path("caveId") caveId: Int,
        @Body request: RequestCaveModifyDto,
    ): ResponseDto

    @POST("/api/v1/member/{memberId}/cave")
    suspend fun postCave(
        @Path("memberId") memberId: Int,
        @Body request: RequestCavePostDto,
    ): ResponsePostCaveDto
}
