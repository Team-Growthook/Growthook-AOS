package com.growthook.aos.data.repository

import com.growthook.aos.data.datasource.remote.SeedDataSource
import com.growthook.aos.domain.repository.SeedRepository
import javax.inject.Inject

class SeedRepositoryImpl @Inject constructor(private val seedDataSource: SeedDataSource) :
    SeedRepository {
    override suspend fun deleteSeed(seedId: Int): Result<Unit> = runCatching {
        seedDataSource.deleteSeed(seedId)
    }
}
