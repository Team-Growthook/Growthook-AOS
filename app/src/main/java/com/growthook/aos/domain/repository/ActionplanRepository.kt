package com.growthook.aos.domain.repository

import com.growthook.aos.domain.entity.Actionplan

interface ActionplanRepository {
    suspend fun getActionplans(seedId: Int): Result<List<Actionplan>>

    suspend fun postActionplans(seedId: Int, contents: List<String>): Result<Unit>
}
