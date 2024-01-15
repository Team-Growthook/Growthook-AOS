package com.growthook.aos.domain.usecase

import com.growthook.aos.domain.repository.SeedRepository
import javax.inject.Inject

class ScrapSeedUseCase @Inject constructor(private val repository: SeedRepository) {

    suspend operator fun invoke(seedId: Int) = repository.scrapSeed(seedId)
}
