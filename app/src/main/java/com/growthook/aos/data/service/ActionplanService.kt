package com.growthook.aos.data.service

import com.growthook.aos.data.model.response.ResponseGetActionplanDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ActionplanService {
    @GET("api/v1/seed/{seedId}/actionplan")
    suspend fun getActionplans(
        @Path("seedId") seedId: Int,
    ): ResponseGetActionplanDto
}
