package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.model.response.ResponseGetCavesDto
import com.growthook.aos.data.model.response.ResponseGetDetailCaveDto

interface CaveDataSource {
    suspend fun deleteCave(caveId: Int): ResponseDto

    suspend fun getCaves(memberId: Int): ResponseGetCavesDto

    suspend fun getCaveDetail(memberId: Int, caveId: Int): ResponseGetDetailCaveDto
}
