package com.growthook.aos.domain.repository

interface SeedRepository {

    suspend fun deleteSeed(seedId: Int): Result<Unit>

    suspend fun moveSeed(seedId: Int, toMoveCaveId: Int): Result<Unit>
}
