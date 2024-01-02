package com.growthook.aos.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestCavePostDto(
    @SerialName("name")
    val name: String,
    @SerialName("introduction")
    val introduction: String,
    @SerialName("isShared")
    val isShared: Boolean
)