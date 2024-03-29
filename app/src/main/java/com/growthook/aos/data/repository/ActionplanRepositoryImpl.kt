package com.growthook.aos.data.repository

import com.growthook.aos.data.datasource.remote.ActionplanDataSource
import com.growthook.aos.data.model.remote.request.RequestActionplanModifyDto
import com.growthook.aos.data.model.remote.request.RequestActionplanPostDto
import com.growthook.aos.data.model.remote.response.ApiResult
import com.growthook.aos.data.model.remote.response.ResponseGetDoingTodo
import com.growthook.aos.data.model.remote.response.ResponseGetDoneTodo
import com.growthook.aos.domain.entity.Actionplan
import com.growthook.aos.domain.repository.ActionplanRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActionplanRepositoryImpl @Inject constructor(private val actionplanDataSource: ActionplanDataSource) :
    ActionplanRepository {
    override suspend fun getActionplans(seedId: Int): Result<List<Actionplan>> = runCatching {
        actionplanDataSource.getActionplans(seedId).toActionplan()
    }

    override suspend fun postActionplans(seedId: Int, contents: List<String>): Result<Unit> =
        runCatching {
            actionplanDataSource.postActionplans(
                seedId,
                RequestActionplanPostDto(contents),
            )
        }

    override suspend fun getDoingActionplans(memberId: Int): Flow<ApiResult<ResponseGetDoingTodo>> =
        actionplanDataSource.getDoingActionplans(memberId)

    override suspend fun getFinishedActionplans(memberId: Int): Flow<ApiResult<ResponseGetDoneTodo>> =
        actionplanDataSource.getFinishedActionplans(memberId)

    override suspend fun completeActionplan(actionplanId: Int): Result<Unit> =
        runCatching {
            actionplanDataSource.completeActionplan(actionplanId)
        }

    override suspend fun modifyActionplan(actionplanId: Int, content: String): Result<Unit> =
        runCatching {
            actionplanDataSource.modifyActionplan(
                actionplanId,
                RequestActionplanModifyDto(content),
            )
        }

    override suspend fun deleteActionplan(actionplanId: Int): Result<Unit> =
        runCatching {
            actionplanDataSource.deleteActionplan(actionplanId)
        }

    override suspend fun getActionplanPercent(memberId: Int): Result<Int> =
        runCatching {
            actionplanDataSource.getActionplanPercent(memberId).data
        }

    override suspend fun scrapActionplan(actionplanId: Int): Result<Unit> =
        runCatching {
            actionplanDataSource.scrapActionplan(actionplanId)
        }
}
