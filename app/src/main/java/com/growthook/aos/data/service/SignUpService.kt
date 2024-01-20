package com.growthook.aos.data.service

import com.growthook.aos.data.model.request.RequestPostAuthDto
import com.growthook.aos.data.model.response.ResponsePostAuthDto
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {

    @POST("api/v1/auth")
    suspend fun signUp(
        @Body request: RequestPostAuthDto,
    ): ResponsePostAuthDto
}
