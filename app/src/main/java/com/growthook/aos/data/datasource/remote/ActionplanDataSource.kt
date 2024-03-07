package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.remote.request.RequestActionplanModifyDto
import com.growthook.aos.data.model.remote.request.RequestActionplanPostDto
import com.growthook.aos.data.model.remote.response.ResponseActionlistDto
import com.growthook.aos.data.model.remote.response.ResponseDataDto
import com.growthook.aos.data.model.remote.response.ResponseDto
import com.growthook.aos.data.model.remote.response.ResponseGetActionplanDto

interface ActionplanDataSource {
    suspend fun getActionplans(seedId: Int): ResponseGetActionplanDto

    suspend fun postActionplans(
        seedId: Int,
        request: com.growthook.aos.data.model.remote.request.RequestActionplanPostDto,
    ): ResponseDto

    suspend fun getDoingActionplans(memberId: Int): com.growthook.aos.data.model.remote.response.ResponseActionlistDto

    suspend fun getFinishedActionplans(memberId: Int): com.growthook.aos.data.model.remote.response.ResponseActionlistDto

    suspend fun getActionplanPercent(memberId: Int): ResponseDataDto

    suspend fun completeActionplan(actionplanId: Int): ResponseDto

    suspend fun modifyActionplan(
        actionplanId: Int,
        request: com.growthook.aos.data.model.remote.request.RequestActionplanModifyDto,
    ): ResponseDto

    suspend fun deleteActionplan(actionplanId: Int): ResponseDto

    suspend fun scrapActionplan(actionplanId: Int): ResponseDto
}
