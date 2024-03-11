package com.growthook.aos.domain.repository

import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.domain.entity.CaveDetail

interface CaveRepository {

    suspend fun deleteCave(caveId: Int): Result<Unit>

    suspend fun getCaves(memberId: Int): Result<List<Cave>>

    suspend fun getCaveDetail(memberId: Int, caveId: Int): Result<CaveDetail>

    suspend fun modifyCave(caveId: Int, name: String, introduction: String): Result<Unit>

    suspend fun postCave(memberId: Int, name: String, introduction: String): Result<Unit>
}
