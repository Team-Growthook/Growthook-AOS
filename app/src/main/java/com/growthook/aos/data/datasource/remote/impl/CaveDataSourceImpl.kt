package com.growthook.aos.data.datasource.remote.impl

import com.growthook.aos.data.datasource.remote.CaveDataSource
import com.growthook.aos.data.model.remote.request.RequestCaveModifyDto
import com.growthook.aos.data.model.remote.request.RequestCavePostDto
import com.growthook.aos.data.model.remote.response.ResponseDto
import com.growthook.aos.data.model.remote.response.ResponseGetCavesDto
import com.growthook.aos.data.model.remote.response.ResponseGetDetailCaveDto
import com.growthook.aos.data.model.remote.response.ResponsePostCaveDto
import com.growthook.aos.data.service.CaveService
import javax.inject.Inject

class CaveDataSourceImpl @Inject constructor(private val apiService: CaveService) : CaveDataSource {
    override suspend fun deleteCave(caveId: Int): ResponseDto =
        apiService.deleteCave(caveId)

    override suspend fun getCaves(memberId: Int): ResponseGetCavesDto =
        apiService.getCaves(memberId)

    override suspend fun getCaveDetail(memberId: Int, caveId: Int): ResponseGetDetailCaveDto =
        apiService.getCaveDetail(memberId, caveId)

    override suspend fun modifyCave(caveId: Int, request: RequestCaveModifyDto): ResponseDto =
        apiService.modifyCave(caveId, request)

    override suspend fun postCave(memberId: Int, request: RequestCavePostDto): ResponsePostCaveDto =
        apiService.postCave(memberId, request)
}
