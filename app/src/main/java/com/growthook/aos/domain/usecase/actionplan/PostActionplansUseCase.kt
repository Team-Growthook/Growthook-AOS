package com.growthook.aos.domain.usecase.actionplan

import com.growthook.aos.domain.repository.ActionplanRepository
import javax.inject.Inject

class PostActionplansUseCase @Inject constructor(private val repository: ActionplanRepository) {
    suspend operator fun invoke(seedId: Int, contents: List<String>) =
        repository.postActionplans(seedId, contents)
}
