package com.growthook.aos.domain.repository

import com.growthook.aos.data.model.request.ExampleRequest
import com.growthook.aos.data.model.response.ExampleResponse

interface ExampleRepository {
    suspend fun postExample(exampleRequest: ExampleRequest): Result<ExampleResponse>
}
