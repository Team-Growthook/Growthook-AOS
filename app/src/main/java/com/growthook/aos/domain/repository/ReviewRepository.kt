package com.growthook.aos.domain.repository

interface ReviewRepository {
    suspend fun getReviewDetail(actionplanId: Int): Result<Unit>
}
