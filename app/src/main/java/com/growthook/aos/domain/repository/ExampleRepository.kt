package com.growthook.aos.domain.repository

import com.growthook.aos.data.model.remote.request.ExampleRequest
import com.growthook.aos.data.model.remote.response.ExampleResponse

interface ExampleRepository {
    suspend fun postExample(exampleRequest: com.growthook.aos.data.model.remote.request.ExampleRequest): Result<com.growthook.aos.data.model.remote.response.ExampleResponse>
}
