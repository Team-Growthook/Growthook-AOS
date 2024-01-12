package com.growthook.aos.domain.repository

interface MemberRepository {

    suspend fun getGatherdThook(memberId: Int): Result<Int>
}
