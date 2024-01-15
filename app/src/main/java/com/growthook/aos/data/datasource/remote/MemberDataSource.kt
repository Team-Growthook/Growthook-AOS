package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.model.response.ResponseGatheredThookDto
import com.growthook.aos.data.model.response.ResponseGetProfileDto
import com.growthook.aos.data.model.response.ResponseGetUsedThook

interface MemberDataSource {

    suspend fun getGatheredThook(memberId: Int): ResponseGatheredThookDto

    suspend fun getUsedThook(memberId: Int): ResponseGetUsedThook

    suspend fun getEmail(memberId: Int): ResponseGetProfileDto

    suspend fun deleteMember(memberId: Int): ResponseDto
}
