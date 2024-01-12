package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.response.ResponseGatheredThookDto
import com.growthook.aos.data.model.response.ResponseGetCavesDto

interface MemberDataSource {

    suspend fun getGatheredThook(memberId: Int): ResponseGatheredThookDto
}
