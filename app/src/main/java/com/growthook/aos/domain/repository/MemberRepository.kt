package com.growthook.aos.domain.repository

interface MemberRepository {

    suspend fun getGatherdThook(memberId: Int): Result<Int>

    suspend fun getUsedThook(memberId: Int): Result<Int>

    suspend fun getEmail(memberId: Int): Result<String>
}
