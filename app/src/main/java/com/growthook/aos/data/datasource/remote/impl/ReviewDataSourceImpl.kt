package com.growthook.aos.data.datasource.remote.impl

import com.growthook.aos.data.datasource.remote.ReviewDataSource
import com.growthook.aos.data.model.request.RequestReviewDto
import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.model.response.ResponseGetReviewDto
import com.growthook.aos.data.service.ReviewService
import javax.inject.Inject

class ReviewDataSourceImpl @Inject constructor(private val apiService: ReviewService) :
    ReviewDataSource {
    override suspend fun getReviewDetail(actionplanId: Int): ResponseGetReviewDto =
        apiService.getReviewDetail(actionplanId)

    override suspend fun postReview(actionplanId: Int, request: RequestReviewDto): ResponseDto =
        apiService.postReview(actionplanId, request)
}
