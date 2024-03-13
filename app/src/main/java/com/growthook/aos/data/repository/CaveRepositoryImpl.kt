package com.growthook.aos.data.repository

import com.growthook.aos.data.datasource.remote.CaveDataSource
import com.growthook.aos.data.model.remote.request.RequestCaveModifyDto
import com.growthook.aos.data.model.remote.request.RequestCavePostDto
import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.domain.entity.CaveDetail
import com.growthook.aos.domain.repository.CaveRepository
import javax.inject.Inject

class CaveRepositoryImpl @Inject constructor(private val caveDataSource: CaveDataSource) :
    CaveRepository {
    override suspend fun deleteCave(caveId: Int): Result<Unit> = runCatching {
        caveDataSource.deleteCave(caveId)
    }

    override suspend fun getCaves(memberId: Int): Result<List<Cave>> = runCatching {
        caveDataSource.getCaves(memberId).toCaves()
    }

    override suspend fun getCaveDetail(memberId: Int, caveId: Int): Result<CaveDetail> =
        runCatching {
            caveDataSource.getCaveDetail(memberId, caveId).toCaveDetail()
        }

    override suspend fun modifyCave(caveId: Int, name: String, introduction: String): Result<Unit> =
        runCatching {
            caveDataSource.modifyCave(
                caveId,
                RequestCaveModifyDto(
                    name,
                    introduction,
                    FIXED_VALUE,
                ),
            )
        }

    override suspend fun postCave(memberId: Int, name: String, introduction: String): Result<Unit> =
        runCatching {
            caveDataSource.postCave(
                memberId,
                RequestCavePostDto(
                    name,
                    introduction,
                    FIXED_VALUE,
                ),
            )
        }

    companion object {
        private const val FIXED_VALUE = true
    }
}
