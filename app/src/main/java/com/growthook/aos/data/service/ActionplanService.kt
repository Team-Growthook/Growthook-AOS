package com.growthook.aos.data.service

import com.growthook.aos.data.model.remote.request.RequestActionplanModifyDto
import com.growthook.aos.data.model.remote.request.RequestActionplanPostDto
import com.growthook.aos.data.model.remote.response.ResponseDataDto
import com.growthook.aos.data.model.remote.response.ResponseDto
import com.growthook.aos.data.model.remote.response.ResponseGetActionplanDto
import com.growthook.aos.data.model.remote.response.ResponseGetDoingTodo
import com.growthook.aos.data.model.remote.response.ResponseGetDoneTodo
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
    ): ResponseGetDoingTodo

    @GET("api/v1/member/{memberId}/finished")
    suspend fun getFinishedActionplans(
        @Path("memberId") memberId: Int,
    ): ResponseGetDoneTodo

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

    @PATCH("api/v1/actionplan/{actionplanId}/scrap")
    suspend fun scrapActionplan(
        @Path("actionplanId") actionplanId: Int,
    ): ResponseDto
}
