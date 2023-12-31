package com.growthook.aos.data.datasource.remote.impl

import com.growthook.aos.data.datasource.remote.SeedDataSource
import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.service.SeedService
import javax.inject.Inject

class SeedDataSourceImpl @Inject constructor(private val apiService: SeedService) : SeedDataSource {
    override suspend fun deleteSeed(seedId: Int): ResponseDto = apiService.deleteSeed(seedId)
}
