package com.growthook.aos.data.datasource.remote.impl

import com.growthook.aos.data.datasource.remote.CaveDataSource
import com.growthook.aos.data.model.response.BaseResponse
import com.growthook.aos.data.service.CaveService
import javax.inject.Inject

class CaveDataSourceImpl @Inject constructor(private val apiService: CaveService) : CaveDataSource {
    override suspend fun deleteCave(caveId: Int): BaseResponse<Unit> = apiService.deleteCave(caveId)
}
