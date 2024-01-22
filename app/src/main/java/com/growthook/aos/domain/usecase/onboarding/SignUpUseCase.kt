package com.growthook.aos.domain.usecase.onboarding

import com.growthook.aos.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: SignUpRepository) {
    suspend operator fun invoke(socialPlatform: String, socialToken: String) =
        repository.signUp(socialPlatform, socialToken)
}
