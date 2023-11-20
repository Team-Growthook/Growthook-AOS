package com.growthook.aos.domain.repository

import com.growthook.aos.data.entity.User

interface UserRepository {

    suspend fun setUserInfo(userName: String)
    suspend fun getUserInfo(): User
}
