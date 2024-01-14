package com.growthook.aos.data.service

import com.growthook.aos.data.model.request.RequestActionplanModifyDto
import com.growthook.aos.data.model.request.RequestActionplanPostDto
import com.growthook.aos.data.model.response.ResponseActionlistDto
import com.growthook.aos.data.model.response.ResponseDataDto
import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.model.response.ResponseGetActionplanDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ActionplanService {
    @GET("api/v1/seed/{seedId}/actionplan")
    suspend fun getActionplans(
        @Path("seedId") seedId: Int,
    ): ResponseGetActionplanDto

    @POST("api/v1/seed/{seedId}/actionplan")
    suspend fun postActionplans(
        @Path("seedId") seedId: Int,
        @Body request: RequestActionplanPostDto,
    ): ResponseDto

    @GET("api/v1/member/{memberId}/doing")
    suspend fun getDoingActionplans(
        @Path("memberId") memberId: Int,
    ): ResponseActionlistDto

    @GET("api/v1/member/{memberId}/finished")
    suspend fun getFinishedActionplans(
        @Path("memberId") memberId: Int,
    ): ResponseActionlistDto

    @GET("api/v1/member/{memberId}/actionplan/percent")
    suspend fun getActionplanPercent(
        @Path("memberId") memberId: Int,
    ): ResponseDataDto

    @PATCH("api/v1/actionplan/{actionplanId}/completion")
    suspend fun completeActionplan(
        @Path("actionplanId") actionplanId: Int,
    ): ResponseDto

    @PATCH("api/v1/actionplan/{actionplanId}")
    suspend fun modifyActionplan(
        @Path("actionplanId") actionplanId: Int,
        @Body request: RequestActionplanModifyDto,
    ): ResponseDto

    @DELETE("api/v1/actionplan/{actionplanId}")
    suspend fun deleteActionplan(
        @Path("actionplanId") actionplanId: Int,
    ): ResponseDto
}
