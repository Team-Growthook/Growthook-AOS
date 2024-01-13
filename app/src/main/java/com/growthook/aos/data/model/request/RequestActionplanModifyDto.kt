package com.growthook.aos.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestActionplanModifyDto(
    @SerialName("content")
    val content: String,
)
