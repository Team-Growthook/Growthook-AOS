package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.request.ExampleRequest
import com.growthook.aos.data.model.response.ExampleResponse
import com.growthook.aos.data.service.ExampleService

class ExampleRemoteDataSource(
    private val exampleService: ExampleService,
) {
    suspend fun postExample(exampleRequestDto: ExampleRequest): ExampleResponse =
        exampleService.postExample(exampleRequestDto)
}
