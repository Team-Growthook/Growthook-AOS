package com.growthook.aos.domain.repository

import com.growthook.aos.domain.entity.Profile

interface MemberRepository {

    suspend fun getGatherdThook(memberId: Int): Result<Int>

    suspend fun getUsedThook(memberId: Int): Result<Int>

    suspend fun getProfile(memberId: Int): Result<Profile>

    suspend fun deleteMember(memberId: Int): Result<Unit>
}
