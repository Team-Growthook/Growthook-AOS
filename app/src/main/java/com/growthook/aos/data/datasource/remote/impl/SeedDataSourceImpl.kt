package com.growthook.aos.data.datasource.remote.impl

import com.growthook.aos.data.datasource.remote.SeedDataSource
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
import com.growthook.aos.data.service.SeedService
import javax.inject.Inject

class SeedDataSourceImpl @Inject constructor(private val apiService: SeedService) : SeedDataSource {
    override suspend fun getSeed(seedId: Int): ResponseGetSeedDto = apiService.getSeed(seedId)

    override suspend fun deleteSeed(seedId: Int): ResponseDto = apiService.deleteSeed(seedId)

    override suspend fun moveSeed(seedId: Int, request: RequestSeedMoveDto): ResponseMoveSeedDto =
        apiService.moveSeed(seedId, request)

    override suspend fun postSeed(caveId: Int, request: RequestSeedPostDto): ResponsePostSeedDto =
        apiService.postSeed(caveId, request)

    override suspend fun getCaveSeeds(caveId: Int): ResponseGetCaveSeedsDto =
        apiService.getCaveSeeds(caveId)

    override suspend fun getSeedAlarm(memberId: Int): ResponseAlarmDto =
        apiService.getSeedAlarm(memberId)

    override suspend fun getSeeds(memberId: Int): ResponseGetSeedsDto =
        apiService.getSeeds(memberId)

    override suspend fun unLockSeed(seedId: Int): ResponseDto = apiService.unLockSeed(seedId)

    override suspend fun scrapSeed(seedId: Int): ResponseDto = apiService.scrapSeed(seedId)

    override suspend fun modifySeed(seedId: Int, request: RequestSeedModifyDto): ResponseDto =
        apiService.modifySeed(seedId, request)
}
