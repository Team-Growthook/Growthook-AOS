package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.remote.response.ResponseDto
import com.growthook.aos.data.model.remote.response.ResponseGatheredThookDto
import com.growthook.aos.data.model.remote.response.ResponseGetProfileDto
import com.growthook.aos.data.model.remote.response.ResponseGetUsedThook

interface MemberDataSource {

    suspend fun getGatheredThook(memberId: Int): ResponseGatheredThookDto

    suspend fun getUsedThook(memberId: Int): ResponseGetUsedThook

    suspend fun getProfile(memberId: Int): ResponseGetProfileDto

    suspend fun deleteMember(memberId: Int): ResponseDto
}
