package com.growthook.aos.domain.usecase.actionplan

import com.growthook.aos.domain.repository.ActionplanRepository
import javax.inject.Inject

class GetFinishedActionplansUseCase @Inject constructor(private val repository: ActionplanRepository) {
    suspend operator fun invoke(memberId: Int) = repository.getFinishedActionplans(memberId)
}
