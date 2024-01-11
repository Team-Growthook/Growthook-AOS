package com.growthook.aos.data.repository

import com.growthook.aos.data.datasource.remote.ActionplanDataSource
import com.growthook.aos.data.model.request.RequestActionplanPostDto
import com.growthook.aos.domain.entity.Actionplan
import com.growthook.aos.domain.repository.ActionplanRepository
import javax.inject.Inject

class ActionplanRepositoryImpl @Inject constructor(private val actionplanDataSource: ActionplanDataSource) :
    ActionplanRepository {
    override suspend fun getActionplans(seedId: Int): Result<List<Actionplan>> = runCatching {
        actionplanDataSource.getActionplans(seedId).toActionplan()
    }

    override suspend fun postActionplans(seedId: Int, contents: List<String>): Result<Unit> =
        runCatching {
            actionplanDataSource.postActionplans(seedId, RequestActionplanPostDto(contents))
        }
}
