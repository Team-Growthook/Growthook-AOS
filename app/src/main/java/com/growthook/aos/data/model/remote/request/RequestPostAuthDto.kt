package com.growthook.aos.data.model.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPostAuthDto(
    @SerialName("socialPlatform")
    val socialPlatform: String,
    @SerialName("socialToken")
    val socialToken: String,
)
