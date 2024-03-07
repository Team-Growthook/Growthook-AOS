package com.growthook.aos.data.repository

import com.growthook.aos.data.datasource.remote.SeedDataSource
import com.growthook.aos.data.model.remote.request.RequestSeedModifyDto
import com.growthook.aos.data.model.remote.request.RequestSeedMoveDto
import com.growthook.aos.data.model.remote.request.RequestSeedPostDto
import com.growthook.aos.domain.entity.Insight
import com.growthook.aos.domain.entity.Seed
import com.growthook.aos.domain.repository.SeedRepository
import javax.inject.Inject

class SeedRepositoryImpl @Inject constructor(private val seedDataSource: SeedDataSource) :
    SeedRepository {
    override suspend fun getSeed(seedId: Int): Result<Seed> = runCatching {
        seedDataSource.getSeed(seedId).toSeedDetail()
    }

    override suspend fun deleteSeed(seedId: Int): Result<Unit> = runCatching {
        seedDataSource.deleteSeed(seedId)
    }

    override suspend fun moveSeed(seedId: Int, toMoveCaveId: Int): Result<Unit> = runCatching {
        seedDataSource.moveSeed(
            seedId,
            RequestSeedMoveDto(toMoveCaveId),
        )
    }

    override suspend fun postSeed(
        caveId: Int,
        insight: String,
        memo: String,
        source: String,
        url: String,
        goalMonth: Int,
    ): Result<Int> = runCatching {
        seedDataSource.postSeed(
            caveId,
            RequestSeedPostDto(
                insight,
                memo,
                source,
                url,
                goalMonth,
            ),
        ).data.seedId
    }

    override suspend fun getCaveSeeds(caveId: Int): Result<List<Insight>> = runCatching {
        seedDataSource.getCaveSeeds(caveId).toInsight()
    }

    override suspend fun getSeedAlarm(memberId: Int): Result<Int> = runCatching {
        seedDataSource.getSeedAlarm(memberId).data.seedCount
    }

    override suspend fun getSeeds(memberId: Int): Result<List<Insight>> = runCatching {
        seedDataSource.getSeeds(memberId).toInsight()
    }

    override suspend fun unLockSeed(seedId: Int): Result<Unit> = runCatching {
        seedDataSource.unLockSeed(seedId)
    }

    override suspend fun scrapSeed(seedId: Int): Result<Unit> = runCatching {
        seedDataSource.scrapSeed(seedId)
    }

    override suspend fun modifySeed(
        seedId: Int,
        insight: String,
        memo: String,
        source: String,
        url: String,
    ): Result<Unit> = runCatching {
        seedDataSource.modifySeed(
            seedId,
            RequestSeedModifyDto(
                insight,
                memo,
                source,
                url,
            ),
        )
    }
}
