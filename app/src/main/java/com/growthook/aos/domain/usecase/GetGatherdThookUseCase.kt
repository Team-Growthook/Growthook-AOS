package com.growthook.aos.domain.usecase

import com.growthook.aos.domain.repository.MemberRepository
import javax.inject.Inject

class GetGatherdThookUseCase @Inject constructor(private val repository: MemberRepository) {

    suspend operator fun invoke(memberId: Int) = repository.getGatherdThook(memberId)
}
