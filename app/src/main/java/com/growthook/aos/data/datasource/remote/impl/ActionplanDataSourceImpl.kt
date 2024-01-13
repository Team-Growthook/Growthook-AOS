package com.growthook.aos.data.datasource.remote.impl

import com.growthook.aos.data.datasource.remote.ActionplanDataSource
import com.growthook.aos.data.model.request.RequestActionplanPostDto
import com.growthook.aos.data.model.response.ResponseActionlistDto
import com.growthook.aos.data.model.response.ResponseDataDto
import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.model.response.ResponseGetActionplanDto
import com.growthook.aos.data.service.ActionplanService
import javax.inject.Inject

class ActionplanDataSourceImpl @Inject constructor(private val apiService: ActionplanService) :
    ActionplanDataSource {
    override suspend fun getActionplans(seedId: Int): ResponseGetActionplanDto =
        apiService.getActionplans(seedId)

    override suspend fun postActionplans(
        seedId: Int,
        request: RequestActionplanPostDto,
    ): ResponseDto =
        apiService.postActionplans(seedId, request)

    override suspend fun getDoingActionplans(memberId: Int): ResponseActionlistDto =
        apiService.getDoingActionplans(memberId)

    override suspend fun getFinishedActionplans(memberId: Int): ResponseActionlistDto =
        apiService.getFinishedActionplans(memberId)

    override suspend fun getActionplanPercent(memberId: Int): ResponseDataDto =
        apiService.getActionplanPercent(memberId)
}
