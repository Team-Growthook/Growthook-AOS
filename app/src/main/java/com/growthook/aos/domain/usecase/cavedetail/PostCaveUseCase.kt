package com.growthook.aos.domain.usecase.cavedetail

import com.growthook.aos.domain.repository.CaveRepository
import javax.inject.Inject

class PostCaveUseCase @Inject constructor(private val repository: CaveRepository) {

    suspend operator fun invoke(memberId: Int, name: String, introduction: String) =
        repository.postCave(memberId, name, introduction)
}
