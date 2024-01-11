package com.growthook.aos.domain.repository

import com.growthook.aos.data.entity.User

interface UserRepository {

    suspend fun setUserInfo(userName: String, memberId: Int, isOnboarding: Boolean)
    suspend fun getUserInfo(): User
}
