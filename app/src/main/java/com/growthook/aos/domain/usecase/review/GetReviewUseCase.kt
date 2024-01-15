package com.growthook.aos.domain.usecase.review

import com.growthook.aos.domain.repository.ReviewRepository
import javax.inject.Inject

class GetReviewUseCase @Inject constructor(private val repository: ReviewRepository) {
    suspend operator fun invoke(actionplanId: Int) = repository.getReviewDetail(actionplanId)
}
