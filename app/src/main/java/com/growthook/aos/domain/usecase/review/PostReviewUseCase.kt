package com.growthook.aos.domain.usecase.review

import com.growthook.aos.domain.repository.ReviewRepository
import javax.inject.Inject

class PostReviewUseCase @Inject constructor(private val repository: ReviewRepository) {
    suspend operator fun invoke(actionplanId: Int, content: String) =
        repository.postReview(actionplanId, content)
}
