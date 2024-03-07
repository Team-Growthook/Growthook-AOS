package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.remote.request.RequestReviewDto
import com.growthook.aos.data.model.remote.response.ResponseDto
import com.growthook.aos.data.model.remote.response.ResponseGetReviewDto

interface ReviewDataSource {
    suspend fun getReviewDetail(actionplanId: Int): ResponseGetReviewDto

    suspend fun postReview(actionplanId: Int, request: RequestReviewDto): ResponseDto
}
