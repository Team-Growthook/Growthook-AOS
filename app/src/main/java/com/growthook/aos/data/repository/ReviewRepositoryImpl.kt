package com.growthook.aos.data.repository

import com.growthook.aos.data.datasource.remote.ReviewDataSource
import com.growthook.aos.data.model.remote.request.RequestReviewDto
import com.growthook.aos.domain.entity.Review
import com.growthook.aos.domain.repository.ReviewRepository
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(private val reviewDataSource: ReviewDataSource) :
    ReviewRepository {
    override suspend fun getReviewDetail(actionplanId: Int): Result<Review> = runCatching {
        reviewDataSource.getReviewDetail(actionplanId).toReview()
    }

    override suspend fun postReview(actionplanId: Int, content: String): Result<Unit> =
        runCatching {
            reviewDataSource.postReview(
                actionplanId,
                RequestReviewDto(content),
            )
        }
}
