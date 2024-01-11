package com.growthook.aos.domain.repository

import com.growthook.aos.domain.entity.Insight

interface SeedRepository {

    suspend fun deleteSeed(seedId: Int): Result<Unit>

    suspend fun moveSeed(seedId: Int, toMoveCaveId: Int): Result<Unit>

    suspend fun postSeed(caveId: Int, insight: String, memo: String, source: String, url: String, goalMonth: Int): Result<Unit>

    suspend fun getCaveSeeds(caveId: Int): Result<List<Insight>>
}
