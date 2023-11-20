package com.growthook.aos.domain.usecase.local

import com.growthook.aos.data.entity.User
import com.growthook.aos.domain.repository.UserRepository
import javax.inject.Inject

class PostUserUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(userName: String) = repository.setUserInfo(userName)
}
