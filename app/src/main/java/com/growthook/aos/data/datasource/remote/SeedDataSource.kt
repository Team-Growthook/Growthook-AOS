package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.request.RequestSeedMoveDto
import com.growthook.aos.data.model.request.RequestSeedPostDto
import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.model.response.ResponseMoveSeedDto
import com.growthook.aos.data.model.response.ResponsePostSeedDto

interface SeedDataSource {
    suspend fun deleteSeed(seedId: Int): ResponseDto

    suspend fun moveSeed(seedId: Int, request: RequestSeedMoveDto): ResponseMoveSeedDto

    suspend fun postSeed(caveId: Int, request: RequestSeedPostDto): ResponsePostSeedDto
}
