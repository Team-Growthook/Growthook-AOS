package com.growthook.aos.domain.repository

interface SignUpRepository {

    suspend fun signUp(socialPlatform: String, socialToken: String): Result<Boolean>
}
