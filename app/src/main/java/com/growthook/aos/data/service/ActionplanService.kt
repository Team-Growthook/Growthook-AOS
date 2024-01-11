package com.growthook.aos.data.service

import com.growthook.aos.data.model.request.RequestActionplanPostDto
import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.model.response.ResponseGetActionplanDto
import retrofit2.http.Body
import retrofit2.http.GET
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
}