package com.growthook.aos.data.service

import com.growthook.aos.data.model.request.RequestReviewDto
import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.model.response.ResponseGetReviewDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewService {
    @GET("api/v1/actionplan/{actionplanId}/review")
    suspend fun getReviewDetail(
        @Path("actionplanId") actionplanId: Int,
    ): ResponseGetReviewDto

    @POST("api/v1/actionplan/{actionplanId}/review")
    suspend fun postReview(
        @Path("actionplanId") actionplanId: Int,
        @Body request: RequestReviewDto,
    ): ResponseDto
}
