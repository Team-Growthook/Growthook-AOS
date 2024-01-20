package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.request.RequestPostAuthDto
import com.growthook.aos.data.model.response.ResponsePostAuthDto

interface SignUpDataSource {

    suspend fun signUp(request: RequestPostAuthDto): ResponsePostAuthDto
}
