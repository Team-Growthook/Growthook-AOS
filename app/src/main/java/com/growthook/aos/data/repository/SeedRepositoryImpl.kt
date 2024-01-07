package com.growthook.aos.data.repository

import com.growthook.aos.data.datasource.remote.SeedDataSource
import com.growthook.aos.data.model.request.RequestSeedMoveDto
import com.growthook.aos.data.model.request.RequestSeedPostDto
import com.growthook.aos.domain.repository.SeedRepository
import javax.inject.Inject

class SeedRepositoryImpl @Inject constructor(private val seedDataSource: SeedDataSource) :
    SeedRepository {
    override suspend fun getSeed(seedId: Int): Result<Unit> = runCatching {
        seedDataSource.getSeed(seedId)
    }

    override suspend fun deleteSeed(seedId: Int): Result<Unit> = runCatching {
        seedDataSource.deleteSeed(seedId)
    }

    override suspend fun moveSeed(seedId: Int, toMoveCaveId: Int): Result<Unit> = runCatching {
        seedDataSource.moveSeed(seedId, RequestSeedMoveDto(toMoveCaveId))
    }

    override suspend fun postSeed(
        caveId: Int,
        insight: String,
        memo: String,
        source: String,
        url: String,
        goalMonth: Int,
    ): Result<Unit> = runCatching {
        seedDataSource.postSeed(caveId, RequestSeedPostDto(insight, memo, source, url, goalMonth))
    }
}
