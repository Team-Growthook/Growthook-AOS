package com.growthook.aos.data.repository

import com.growthook.aos.data.service.MemberService
import com.growthook.aos.domain.entity.Profile
import com.growthook.aos.domain.repository.MemberRepository
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(private val apiService: MemberService) :
    MemberRepository {
    override suspend fun getGatherdThook(memberId: Int): Result<Int> = runCatching {
        apiService.getGatheredThook(memberId).data.gatheredThook
    }

    override suspend fun getUsedThook(memberId: Int): Result<Int> = runCatching {
        apiService.getUsedThook(memberId).data.usedThook
    }

    override suspend fun getProfile(memberId: Int): Result<Profile> = runCatching {
        apiService.getProfile(memberId).toProfile()
    }

    override suspend fun deleteMember(memberId: Int): Result<Unit> = runCatching {
        apiService.deleteMember(memberId)
    }
}
