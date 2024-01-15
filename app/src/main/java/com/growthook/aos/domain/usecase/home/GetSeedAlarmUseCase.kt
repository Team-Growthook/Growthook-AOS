package com.growthook.aos.domain.usecase.home

import com.growthook.aos.domain.repository.SeedRepository
import javax.inject.Inject

class GetSeedAlarmUseCase @Inject constructor(private val repository: SeedRepository) {

    suspend operator fun invoke(memberId: Int) = repository.getSeedAlarm(memberId)
}
