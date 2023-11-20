package com.growthook.aos.domain.repository

import com.growthook.aos.data.entity.User

interface UserRepository {

    suspend fun setUserInfo(userInfo: User)
    suspend fun getUserInfo(): User
}
