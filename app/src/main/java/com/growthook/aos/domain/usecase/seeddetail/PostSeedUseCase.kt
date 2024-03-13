package com.growthook.aos.domain.usecase.seeddetail

import com.growthook.aos.domain.repository.SeedRepository
import javax.inject.Inject

class PostSeedUseCase @Inject constructor(private val repository: SeedRepository) {

    suspend operator fun invoke(
        caveId: Int,
        insight: String,
        memo: String,
        source: String,
        url: String,
        goalMonth: Int,
    ) =
        repository.postSeed(caveId, insight, memo, source, url, goalMonth)
}
