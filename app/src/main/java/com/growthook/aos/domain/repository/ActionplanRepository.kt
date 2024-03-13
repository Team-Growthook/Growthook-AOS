package com.growthook.aos.domain.repository

import com.growthook.aos.data.model.remote.response.ApiResult
import com.growthook.aos.data.model.remote.response.ResponseGetDoingTodo
import com.growthook.aos.data.model.remote.response.ResponseGetDoneTodo
import com.growthook.aos.domain.entity.Actionplan
import kotlinx.coroutines.flow.Flow

interface ActionplanRepository {
    suspend fun getActionplans(seedId: Int): Result<List<Actionplan>>

    suspend fun postActionplans(seedId: Int, contents: List<String>): Result<Unit>

    suspend fun getDoingActionplans(memberId: Int): Flow<ApiResult<ResponseGetDoingTodo>>

    suspend fun getFinishedActionplans(memberId: Int): Flow<ApiResult<ResponseGetDoneTodo>>

    suspend fun completeActionplan(actionplanId: Int): Result<Unit>

    suspend fun modifyActionplan(actionplanId: Int, content: String): Result<Unit>

    suspend fun deleteActionplan(actionplanId: Int): Result<Unit>
    suspend fun getActionplanPercent(memberId: Int): Result<Int>

    suspend fun scrapActionplan(actionplanId: Int): Result<Unit>
}
