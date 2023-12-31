package com.growthook.aos.domain.usecase.cavedetail

import com.growthook.aos.domain.repository.CaveRepository
import javax.inject.Inject

class GetCaveDetailUseCase @Inject constructor(private val repository: CaveRepository) {
    suspend operator fun invoke(memberId: Int, caveId: Int) =
        repository.getCaveDetail(memberId, caveId)
}
