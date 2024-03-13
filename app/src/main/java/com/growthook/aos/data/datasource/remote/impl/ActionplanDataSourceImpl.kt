package com.growthook.aos.data.datasource.remote.impl

import com.growthook.aos.data.datasource.remote.ActionplanDataSource
import com.growthook.aos.data.model.remote.request.RequestActionplanModifyDto
import com.growthook.aos.data.model.remote.request.RequestActionplanPostDto
import com.growthook.aos.data.model.remote.response.ApiResult
import com.growthook.aos.data.model.remote.response.ResponseDataDto
import com.growthook.aos.data.model.remote.response.ResponseDto
import com.growthook.aos.data.model.remote.response.ResponseGetActionplanDto
import com.growthook.aos.data.model.remote.response.ResponseGetDoingTodo
import com.growthook.aos.data.model.remote.response.ResponseGetDoneTodo
import com.growthook.aos.data.model.remote.response.safeFlow
import com.growthook.aos.data.service.ActionplanService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActionplanDataSourceImpl @Inject constructor(
    private val apiService: ActionplanService,
) :
    ActionplanDataSource {
    override suspend fun getActionplans(seedId: Int): ResponseGetActionplanDto =
        apiService.getActionplans(seedId)

    override suspend fun postActionplans(
        seedId: Int,
        request: RequestActionplanPostDto,
    ): ResponseDto =
        apiService.postActionplans(seedId, request)

    override suspend fun getDoingActionplans(memberId: Int): Flow<ApiResult<ResponseGetDoingTodo>> =
        safeFlow {
            apiService.getDoingActionplans(memberId)
        }

    override suspend fun getFinishedActionplans(memberId: Int): Flow<ApiResult<ResponseGetDoneTodo>> =
        safeFlow {
            apiService.getFinishedActionplans(memberId)
        }

    override suspend fun getActionplanPercent(memberId: Int): ResponseDataDto =
        apiService.getActionplanPercent(memberId)

    override suspend fun completeActionplan(actionplanId: Int): ResponseDto =
        apiService.completeActionplan(actionplanId)

    override suspend fun modifyActionplan(
        actionplanId: Int,
        request: RequestActionplanModifyDto,
    ): ResponseDto =
        apiService.modifyActionplan(actionplanId, request)

    override suspend fun deleteActionplan(actionplanId: Int): ResponseDto =
        apiService.deleteActionplan(actionplanId)

    override suspend fun scrapActionplan(actionplanId: Int): ResponseDto =
        apiService.scrapActionplan(actionplanId)
}
