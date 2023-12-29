package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.response.BaseResponse

interface CaveDataSource {
    suspend fun deleteCave(caveId: Int): BaseResponse<Unit>
}
