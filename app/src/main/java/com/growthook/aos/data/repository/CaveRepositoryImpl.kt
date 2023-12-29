package com.growthook.aos.data.repository

import com.growthook.aos.data.datasource.remote.CaveDataSource
import com.growthook.aos.domain.repository.CaveRepository
import javax.inject.Inject

class CaveRepositoryImpl @Inject constructor(private val caveDataSource: CaveDataSource) :CaveRepository {
    override suspend fun deleteCave(caveId: Int): Result<Unit> = kotlin.runCatching {
        caveDataSource.deleteCave(caveId)
    }
}