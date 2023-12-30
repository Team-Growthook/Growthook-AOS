package com.growthook.aos.domain.usecase.home

import com.growthook.aos.domain.repository.CaveRepository
import javax.inject.Inject

class GetCavesUseCase @Inject constructor(private val repository: CaveRepository) {
    suspend operator fun invoke(memberId: Int) = repository.getCaves(memberId)
}
