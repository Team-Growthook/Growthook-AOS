package com.growthook.aos.data.datasource.local

import com.growthook.aos.data.model.remote.request.ExampleRequest
import com.growthook.aos.data.model.remote.response.ExampleResponse
import com.growthook.aos.data.service.ExampleService

class ExampleLocalDataSource(
    private val exampleService: ExampleService,
) {
    suspend fun postExample(exampleRequestDto: com.growthook.aos.data.model.remote.request.ExampleRequest): com.growthook.aos.data.model.remote.response.ExampleResponse =
        exampleService.postExample(exampleRequestDto)
}
