package com.growthook.aos.data.datasource.remote.impl

import com.growthook.aos.data.datasource.remote.RefreshDataSource
import com.growthook.aos.data.model.remote.response.ResponseRefreshTokenDto
import com.growthook.aos.data.service.RefreshService
import javax.inject.Inject

class RefreshDataSourceImpl @Inject constructor(private val apiService: RefreshService) :
    RefreshDataSource {
    override suspend fun getRefreshToken(): ResponseRefreshTokenDto = apiService.getRefreshToken()
}
