package com.growthook.aos.data.datasource.remote.impl

import com.growthook.aos.data.datasource.remote.ActionplanDataSource
import com.growthook.aos.data.model.response.ResponseGetActionplanDto
import com.growthook.aos.data.service.ActionplanService
import javax.inject.Inject

class ActionplanDataSourceImpl @Inject constructor(private val apiService: ActionplanService) :
    ActionplanDataSource {
    override suspend fun getActionplans(seedId: Int): ResponseGetActionplanDto =
        apiService.getActionplans(seedId)
}
