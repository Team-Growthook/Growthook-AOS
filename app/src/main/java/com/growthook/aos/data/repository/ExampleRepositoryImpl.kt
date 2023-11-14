package com.growthook.aos.data.repository

import com.growthook.aos.data.datasource.remote.ExampleRemoteDataSource
import com.growthook.aos.data.model.request.ExampleRequest
import com.growthook.aos.data.model.response.ExampleResponse
import com.growthook.aos.domain.repository.ExampleRepository

class ExampleRepositoryImpl(
    private val exampleDataSource: ExampleRemoteDataSource,
) : ExampleRepository {
    override suspend fun postExample(exampleRequest: ExampleRequest): Result<ExampleResponse> =
        kotlin.runCatching { exampleDataSource.postExample(exampleRequest) }
}
