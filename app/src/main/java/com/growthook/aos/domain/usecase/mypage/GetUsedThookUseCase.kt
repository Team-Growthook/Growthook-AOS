package com.growthook.aos.domain.usecase.mypage

import com.growthook.aos.domain.repository.MemberRepository
import javax.inject.Inject

class GetUsedThookUseCase @Inject constructor(private val repository: MemberRepository) {

    suspend operator fun invoke(memberId: Int) = repository.getUsedThook(memberId)
}
