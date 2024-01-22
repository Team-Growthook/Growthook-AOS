package com.growthook.aos.data.repository

import com.growthook.aos.data.datasource.remote.RefreshDataSource
import com.growthook.aos.data.entity.Token
import com.growthook.aos.domain.repository.RefreshRepository
import com.growthook.aos.domain.repository.TokenRepository
import javax.inject.Inject

class RefreshRepository @Inject constructor(
    private val dataSource: RefreshDataSource,
    private val tokenRepository: TokenRepository,
) :
    RefreshRepository {
    override suspend fun getRefreshToken(): Result<Token> = runCatching {
        val token = dataSource.getRefreshToken().toRefresh()
        tokenRepository.setToken(token.accessToken ?: "", token.refreshToken ?: "")
        token
    }
}
