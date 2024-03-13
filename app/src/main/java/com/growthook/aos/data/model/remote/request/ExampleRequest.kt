package com.growthook.aos.data.model.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class ExampleRequest(
    val id: Int,
    val title: String,
    val content: String,
)
