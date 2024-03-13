package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.remote.request.RequestPostAuthDto
import com.growthook.aos.data.model.remote.response.ResponsePostAuthDto

interface SignUpDataSource {

    suspend fun signUp(request: RequestPostAuthDto): ResponsePostAuthDto
}
