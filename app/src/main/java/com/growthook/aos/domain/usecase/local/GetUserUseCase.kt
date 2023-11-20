package com.growthook.aos.domain.usecase.local

import com.growthook.aos.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke() = repository.getUserInfo()
}
