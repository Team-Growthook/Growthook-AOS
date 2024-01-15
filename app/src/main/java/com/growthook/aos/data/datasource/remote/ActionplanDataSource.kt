package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.request.RequestActionplanModifyDto
import com.growthook.aos.data.model.request.RequestActionplanPostDto
import com.growthook.aos.data.model.response.ResponseActionlistDto
import com.growthook.aos.data.model.response.ResponseDataDto
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

    suspend fun getActionplanPercent(memberId: Int): ResponseDataDto

    suspend fun completeActionplan(actionplanId: Int): ResponseDto

    suspend fun modifyActionplan(
        actionplanId: Int,
        request: RequestActionplanModifyDto,
    ): ResponseDto

    suspend fun deleteActionplan(actionplanId: Int): ResponseDto
}
