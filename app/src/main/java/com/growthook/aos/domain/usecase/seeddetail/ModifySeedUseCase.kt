package com.growthook.aos.domain.usecase.seeddetail

import com.growthook.aos.domain.repository.SeedRepository
import javax.inject.Inject

class ModifySeedUseCase @Inject constructor(
    private val repository: SeedRepository,
) {

    suspend operator fun invoke(
        seedId: Int,
        insight: String,
        memo: String,
        source: String,
        url: String,
    ) = repository.modifySeed(seedId, insight, memo, source, url)
}
