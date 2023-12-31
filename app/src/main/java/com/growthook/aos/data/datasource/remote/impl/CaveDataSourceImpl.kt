package com.growthook.aos.data.datasource.remote.impl

import com.growthook.aos.data.datasource.remote.CaveDataSource
import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.model.response.ResponseGetCavesDto
import com.growthook.aos.data.model.response.ResponseGetDetailCaveDto
import com.growthook.aos.data.service.CaveService
import javax.inject.Inject

class CaveDataSourceImpl @Inject constructor(private val apiService: CaveService) : CaveDataSource {
    override suspend fun deleteCave(caveId: Int): ResponseDto =
        apiService.deleteCave(caveId)

    override suspend fun getCaves(memberId: Int): ResponseGetCavesDto =
        apiService.getCaves(memberId)

    override suspend fun getCaveDetail(memberId: Int, caveId: Int): ResponseGetDetailCaveDto =
        apiService.getCaveDetail(memberId, caveId)
}
