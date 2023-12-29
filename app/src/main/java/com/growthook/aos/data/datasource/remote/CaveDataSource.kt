package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.response.BaseResponse
import com.growthook.aos.data.model.response.ResponseGetCavesDto

interface CaveDataSource {
    suspend fun deleteCave(caveId: Int): BaseResponse<Unit>

    suspend fun getCaves(memberId: Int): BaseResponse<List<ResponseGetCavesDto>>
}
