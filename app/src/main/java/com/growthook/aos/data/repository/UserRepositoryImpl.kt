package com.growthook.aos.data.repository

import androidx.datastore.core.DataStore
import com.growthook.aos.data.entity.User
import com.growthook.aos.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val dataStore: DataStore<User>) :
    UserRepository {
    override suspend fun setUserInfo(userName: String, memberId: Int) {
        dataStore.updateData { User(userName, memberId) }
    }

    override suspend fun getUserInfo(): User = dataStore.data.first()
}
