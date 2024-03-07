package com.growthook.aos.data.repository

import com.growthook.aos.data.datasource.remote.ExampleRemoteDataSource
import com.growthook.aos.data.model.remote.request.ExampleRequest
import com.growthook.aos.data.model.remote.response.ExampleResponse
import com.growthook.aos.domain.repository.ExampleRepository

class ExampleRepositoryImpl(
    private val exampleDataSource: ExampleRemoteDataSource,
) : ExampleRepository {
    override suspend fun postExample(exampleRequest: com.growthook.aos.data.model.remote.request.ExampleRequest): Result<com.growthook.aos.data.model.remote.response.ExampleResponse> =
        kotlin.runCatching { exampleDataSource.postExample(exampleRequest) }
}
