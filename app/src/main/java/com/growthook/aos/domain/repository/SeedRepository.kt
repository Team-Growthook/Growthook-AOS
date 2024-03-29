package com.growthook.aos.domain.repository

import com.growthook.aos.domain.entity.Insight
import com.growthook.aos.domain.entity.Seed

interface SeedRepository {
    suspend fun getSeed(seedId: Int): Result<Seed>

    suspend fun deleteSeed(seedId: Int): Result<Unit>

    suspend fun moveSeed(seedId: Int, toMoveCaveId: Int): Result<Unit>

    suspend fun postSeed(
        caveId: Int,
        insight: String,
        memo: String,
        source: String,
        url: String,
        goalMonth: Int,
    ): Result<Int>

    suspend fun getCaveSeeds(caveId: Int): Result<List<Insight>>

    suspend fun getSeedAlarm(memberId: Int): Result<Int>

    suspend fun getSeeds(memberId: Int): Result<List<Insight>>

    suspend fun unLockSeed(seedId: Int): Result<Unit>

    suspend fun scrapSeed(seedId: Int): Result<Unit>

    suspend fun modifySeed(
        seedId: Int,
        insight: String,
        memo: String,
        source: String,
        url: String,
    ): Result<Unit>
}
