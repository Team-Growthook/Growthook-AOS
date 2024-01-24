package com.growthook.aos.data.datasource.remote.impl

import com.growthook.aos.data.datasource.remote.MemberDataSource
import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.model.response.ResponseGatheredThookDto
import com.growthook.aos.data.model.response.ResponseGetProfileDto
import com.growthook.aos.data.model.response.ResponseGetUsedThook
import com.growthook.aos.data.service.MemberService
import javax.inject.Inject

class MemberDataSourceImpl @Inject constructor(private val apiService: MemberService) :
    MemberDataSource {
    override suspend fun getGatheredThook(memberId: Int): ResponseGatheredThookDto =
        apiService.getGatheredThook(memberId)

    override suspend fun getUsedThook(memberId: Int): ResponseGetUsedThook =
        apiService.getUsedThook(memberId)

    override suspend fun getProfile(memberId: Int): ResponseGetProfileDto =
        apiService.getProfile(memberId)

    override suspend fun deleteMember(memberId: Int): ResponseDto =
        apiService.deleteMember(memberId)
}
