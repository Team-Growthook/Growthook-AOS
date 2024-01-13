package com.growthook.aos.data.service

import com.growthook.aos.data.model.response.ResponseGetReviewDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ReviewService {
    @GET("api/v1/actionplan/{actionplanId}/review")
    suspend fun getReviewDetail(
        @Path("actionplanId") actionplanId: Int,
    ): ResponseGetReviewDto
}
