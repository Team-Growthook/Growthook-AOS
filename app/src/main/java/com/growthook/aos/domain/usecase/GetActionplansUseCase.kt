package com.growthook.aos.domain.usecase

import com.growthook.aos.domain.repository.ActionplanRepository
import javax.inject.Inject

class GetActionplansUseCase @Inject constructor(private val repository: ActionplanRepository) {
    suspend operator fun invoke(seedId: Int) = repository.getActionplans(seedId)
}
