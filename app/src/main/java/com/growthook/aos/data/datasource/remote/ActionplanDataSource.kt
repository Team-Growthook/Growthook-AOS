package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.request.RequestActionplanPostDto
import com.growthook.aos.data.model.response.ResponseActionlistDto
import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.model.response.ResponseGetActionplanDto

interface ActionplanDataSource {
    suspend fun getActionplans(seedId: Int): ResponseGetActionplanDto

    suspend fun postActionplans(
        seedId: Int,
        request: RequestActionplanPostDto,
    ): ResponseDto

    suspend fun getDoingActionplans(memberId: Int): ResponseActionlistDto

    suspend fun getFinishedActionplans(memberId: Int): ResponseActionlistDto
}
