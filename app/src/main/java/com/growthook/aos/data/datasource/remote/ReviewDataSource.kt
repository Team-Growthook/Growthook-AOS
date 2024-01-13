package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.response.ResponseGetReviewDto

interface ReviewDataSource {
    suspend fun getReviewDetail(actionplanId: Int): ResponseGetReviewDto
}
