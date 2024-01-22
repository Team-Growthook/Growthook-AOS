package com.growthook.aos.domain.usecase.local

import com.growthook.aos.domain.repository.TokenRepository
import javax.inject.Inject

class PostTokenUseCase @Inject constructor(private val repository: TokenRepository) {
    suspend operator fun invoke(accessToken: String, refreshToken: String) =
        repository.setToken(accessToken, refreshToken)
}
