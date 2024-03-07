package com.growthook.aos.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseAlarmDto(
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: SeedCount,
) {
    @Serializable
    data class SeedCount(
        @SerialName("seedCount")
        val seedCount: Int,
    )
}
