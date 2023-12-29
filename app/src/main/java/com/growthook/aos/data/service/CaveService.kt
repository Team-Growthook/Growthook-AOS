package com.growthook.aos.data.service

import com.growthook.aos.data.model.response.BaseResponse
import retrofit2.http.DELETE
import retrofit2.http.Path

interface CaveService {

    @DELETE("api/v1/cave/{caveId}")
    suspend fun deleteCave(
        @Path("caveId") caveId: Int,
    ): BaseResponse<Unit>
}
