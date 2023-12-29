package com.growthook.aos.domain.repository

interface CaveRepository {

    suspend fun deleteCave(caveId: Int): Result<Unit>
}
