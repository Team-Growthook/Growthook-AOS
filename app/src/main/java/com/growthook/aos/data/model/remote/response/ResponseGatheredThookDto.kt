package com.growthook.aos.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGatheredThookDto(
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: GatheredThook,
) {
    @Serializable
    data class GatheredThook(
        @SerialName("gatheredSsuk")
        val gatheredThook: Int,
    )
}
