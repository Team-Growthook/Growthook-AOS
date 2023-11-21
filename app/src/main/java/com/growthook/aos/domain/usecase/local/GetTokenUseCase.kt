package com.growthook.aos.domain.usecase.local

import com.growthook.aos.domain.repository.TokenRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val repository: TokenRepository,
) {

    suspend operator fun invoke() = repository.getToken()
}
