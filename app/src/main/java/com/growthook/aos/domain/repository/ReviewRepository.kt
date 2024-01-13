package com.growthook.aos.domain.repository

import com.growthook.aos.domain.entity.Review

interface ReviewRepository {
    suspend fun getReviewDetail(actionplanId: Int): Result<Review>
}
