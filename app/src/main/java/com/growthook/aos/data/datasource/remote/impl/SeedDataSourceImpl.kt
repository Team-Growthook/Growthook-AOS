package com.growthook.aos.data.datasource.remote.impl

import com.growthook.aos.data.datasource.remote.SeedDataSource
import com.growthook.aos.data.model.request.RequestSeedMoveDto
import com.growthook.aos.data.model.request.RequestSeedPostDto
import com.growthook.aos.data.model.response.ResponseAlarmDto
import com.growthook.aos.data.model.response.ResponseDataDto
import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.model.response.ResponseGetCaveSeedsDto
import com.growthook.aos.data.model.response.ResponseGetSeedDto
import com.growthook.aos.data.model.response.ResponseGetSeedsDto
import com.growthook.aos.data.model.response.ResponseMoveSeedDto
import com.growthook.aos.data.model.response.ResponsePostSeedDto
import com.growthook.aos.data.service.SeedService
import javax.inject.Inject

class SeedDataSourceImpl @Inject constructor(private val apiService: SeedService) : SeedDataSource {
    override suspend fun getSeed(seedId: Int): ResponseGetSeedDto = apiService.getSeed(seedId)

    override suspend fun deleteSeed(seedId: Int): ResponseDto = apiService.deleteSeed(seedId)

    override suspend fun moveSeed(seedId: Int, request: RequestSeedMoveDto): ResponseMoveSeedDto =
        apiService.moveSeed(seedId, request)

    override suspend fun postSeed(caveId: Int, request: RequestSeedPostDto): ResponseDataDto =
        apiService.postSeed(caveId, request)

    override suspend fun getCaveSeeds(caveId: Int): ResponseGetCaveSeedsDto =
        apiService.getCaveSeeds(caveId)

    override suspend fun getSeedAlarm(memberId: Int): ResponseAlarmDto =
        apiService.getSeedAlarm(memberId)

    override suspend fun getSeeds(memberId: Int): ResponseGetSeedsDto =
        apiService.getSeeds(memberId)

    override suspend fun unLockSeed(seedId: Int): ResponseDto = apiService.unLockSeed(seedId)

    override suspend fun scrapSeed(seedId: Int): ResponseDto = apiService.scrapSeed(seedId)
}
