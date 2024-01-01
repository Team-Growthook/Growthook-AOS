package com.growthook.aos.domain.usecase.cavedetail

import com.growthook.aos.domain.repository.CaveRepository
import javax.inject.Inject

class ModifyCaveUseCase @Inject constructor(private val repository: CaveRepository) {
    suspend operator fun invoke(caveId: Int, name: String, introduction: String) =
        repository.modifyCave(caveId, name, introduction)
}
