package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.response.ResponseDto

interface SeedDataSource {
    suspend fun deleteSeed(seedId: Int): ResponseDto
}
