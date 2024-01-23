package com.growthook.aos.data.repository

import com.growthook.aos.data.datasource.remote.SignUpDataSource
import com.growthook.aos.data.model.request.RequestPostAuthDto
import com.growthook.aos.domain.repository.SignUpRepository
import com.growthook.aos.domain.repository.TokenRepository
import com.growthook.aos.domain.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val signUpDataSource: SignUpDataSource,
) : SignUpRepository {
    override suspend fun signUp(socialPlatform: String, socialToken: String): Result<Boolean> =
        runCatching {
            signUpDataSource.signUp(RequestPostAuthDto(socialPlatform, socialToken))
        }.fold(
            onSuccess = {
                userRepository.setUserInfo(it.data.nickname, it.data.memberId)
                tokenRepository.setToken(it.data.accessToken, it.data.refreshToken)
                Result.success(true)
            },
            onFailure = { exception ->
                Timber.d(exception.message)
                Result.failure(exception)
            },
        )
}
