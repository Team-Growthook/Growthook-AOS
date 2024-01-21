package com.growthook.aos.data.repository

import com.growthook.aos.data.datasource.remote.RefreshDataSource
import com.growthook.aos.data.entity.Token
import com.growthook.aos.domain.repository.RefreshRepository
import javax.inject.Inject

class RefreshRepository @Inject constructor(private val dataSource: RefreshDataSource) :
    RefreshRepository {
    override suspend fun getRefreshToken(): Result<Token> = runCatching {
        dataSource.getRefreshToken().toRefresh()
    }
}
