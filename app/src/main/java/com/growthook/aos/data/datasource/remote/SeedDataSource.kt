package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.remote.request.RequestSeedModifyDto
import com.growthook.aos.data.model.remote.request.RequestSeedMoveDto
import com.growthook.aos.data.model.remote.request.RequestSeedPostDto
import com.growthook.aos.data.model.remote.response.ResponseAlarmDto
import com.growthook.aos.data.model.remote.response.ResponseDto
import com.growthook.aos.data.model.remote.response.ResponseGetCaveSeedsDto
import com.growthook.aos.data.model.remote.response.ResponseGetSeedDto
import com.growthook.aos.data.model.remote.response.ResponseGetSeedsDto
import com.growthook.aos.data.model.remote.response.ResponseMoveSeedDto
import com.growthook.aos.data.model.remote.response.ResponsePostSeedDto

interface SeedDataSource {

    suspend fun getSeed(seedId: Int): ResponseGetSeedDto
    suspend fun deleteSeed(seedId: Int): ResponseDto

    suspend fun moveSeed(seedId: Int, request: RequestSeedMoveDto): ResponseMoveSeedDto

    suspend fun postSeed(caveId: Int, request: RequestSeedPostDto): ResponsePostSeedDto

    suspend fun getCaveSeeds(caveId: Int): ResponseGetCaveSeedsDto

    suspend fun getSeedAlarm(memberId: Int): ResponseAlarmDto

    suspend fun getSeeds(memberId: Int): ResponseGetSeedsDto

    suspend fun unLockSeed(seedId: Int): ResponseDto

    suspend fun scrapSeed(seedId: Int): ResponseDto

    suspend fun modifySeed(
        seedId: Int,
        request: RequestSeedModifyDto,
    ): ResponseDto
}
