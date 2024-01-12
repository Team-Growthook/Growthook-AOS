package com.growthook.aos.domain.usecase

import com.growthook.aos.domain.repository.SeedRepository
import javax.inject.Inject

class GetSeedsUseCase @Inject constructor(private val repository: SeedRepository) {

    suspend operator fun invoke(memberId: Int) = repository.getSeeds(memberId)
}
