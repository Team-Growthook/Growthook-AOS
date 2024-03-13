package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.remote.request.RequestActionplanModifyDto
import com.growthook.aos.data.model.remote.request.RequestActionplanPostDto
import com.growthook.aos.data.model.remote.response.ApiResult
import com.growthook.aos.data.model.remote.response.ResponseDataDto
import com.growthook.aos.data.model.remote.response.ResponseDto
import com.growthook.aos.data.model.remote.response.ResponseGetActionplanDto
import com.growthook.aos.data.model.remote.response.ResponseGetDoingTodo
import com.growthook.aos.data.model.remote.response.ResponseGetDoneTodo
import kotlinx.coroutines.flow.Flow

interface ActionplanDataSource {
    suspend fun getActionplans(seedId: Int): ResponseGetActionplanDto

    suspend fun postActionplans(
        seedId: Int,
        request: RequestActionplanPostDto,
    ): ResponseDto

    suspend fun getDoingActionplans(memberId: Int): Flow<ApiResult<ResponseGetDoingTodo>>

    suspend fun getFinishedActionplans(memberId: Int): Flow<ApiResult<ResponseGetDoneTodo>>

    suspend fun getActionplanPercent(memberId: Int): ResponseDataDto

    suspend fun completeActionplan(actionplanId: Int): ResponseDto

    suspend fun modifyActionplan(
        actionplanId: Int,
        request: RequestActionplanModifyDto,
    ): ResponseDto

    suspend fun deleteActionplan(actionplanId: Int): ResponseDto

    suspend fun scrapActionplan(actionplanId: Int): ResponseDto
}
