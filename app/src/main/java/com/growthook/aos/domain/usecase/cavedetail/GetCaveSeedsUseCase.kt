package com.growthook.aos.domain.usecase.cavedetail

import com.growthook.aos.domain.repository.CaveRepository
import com.growthook.aos.domain.repository.SeedRepository
import javax.inject.Inject

class GetCaveSeedsUseCase @Inject constructor(private val repository: SeedRepository) {

    suspend operator fun invoke(caveId: Int) = repository.getCaveSeeds(caveId)
}
