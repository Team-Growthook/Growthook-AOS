package com.growthook.aos.domain.repository

interface SeedRepository {

    suspend fun deleteSeed(seedId: Int): Result<Unit>
}
