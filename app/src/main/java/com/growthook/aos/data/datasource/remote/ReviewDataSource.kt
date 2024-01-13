package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.response.ResponseDto

interface ReviewDataSource {
    suspend fun getReviewDetail(actionplanId: Int): ResponseDto
}
