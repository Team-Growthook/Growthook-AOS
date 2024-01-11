package com.growthook.aos.domain.usecase.seeddetail

import com.growthook.aos.domain.repository.SeedRepository
import javax.inject.Inject

class GetSeedUseCase @Inject constructor(private val repository: SeedRepository) {

    suspend operator fun invoke(seedId: Int) = repository.getSeed(seedId)
}
