package com.growthook.aos.data.datasource.remote.impl

import com.growthook.aos.data.datasource.remote.SignUpDataSource
import com.growthook.aos.data.model.request.RequestPostAuthDto
import com.growthook.aos.data.model.response.ResponsePostAuthDto
import com.growthook.aos.data.service.SignUpService
import javax.inject.Inject

class SignUpDataSourceImpl @Inject constructor(private val apiService: SignUpService) : SignUpDataSource {
    override suspend fun signUp(request: RequestPostAuthDto): ResponsePostAuthDto =
        apiService.signUp(request)
}
