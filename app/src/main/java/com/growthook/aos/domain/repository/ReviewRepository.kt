package com.growthook.aos.domain.repository

import com.growthook.aos.domain.entity.Review

interface ReviewRepository {
    suspend fun getReviewDetail(actionplanId: Int): Result<Review>
    suspend fun postReview(actionplanId: Int, content: String): Result<Unit>
}
