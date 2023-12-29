package com.growthook.aos.domain.repository

import com.growthook.aos.domain.entity.Cave

interface CaveRepository {

    suspend fun deleteCave(caveId: Int): Result<Unit>

    suspend fun getCaves(memberId: Int): Result<List<Cave>>
}
