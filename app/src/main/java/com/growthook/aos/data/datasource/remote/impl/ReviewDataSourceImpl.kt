package com.growthook.aos.data.datasource.remote.impl

import com.growthook.aos.data.datasource.remote.ReviewDataSource
import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.service.ReviewService
import javax.inject.Inject

class ReviewDataSourceImpl @Inject constructor(private val apiService: ReviewService) :
    ReviewDataSource {
    override suspend fun getReviewDetail(actionplanId: Int): ResponseDto =
        apiService.getReviewDetail(actionplanId)
}
