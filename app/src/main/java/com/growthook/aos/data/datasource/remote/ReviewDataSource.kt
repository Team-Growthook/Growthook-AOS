package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.request.RequestReviewDto
import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.model.response.ResponseGetReviewDto

interface ReviewDataSource {
    suspend fun getReviewDetail(actionplanId: Int): ResponseGetReviewDto

    suspend fun postReview(actionplanId: Int, request: RequestReviewDto): ResponseDto
}
