package com.growthook.aos.domain.usecase

import com.growthook.aos.domain.repository.SeedRepository
import javax.inject.Inject

class MoveSeedUseCase @Inject constructor(private val repository: SeedRepository) {
    suspend operator fun invoke(seedId: Int, toMoveCaveId: Int) =
        repository.moveSeed(seedId, toMoveCaveId)
}
