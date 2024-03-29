package com.growthook.aos.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePostSeedDto(
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: SeedId,
) {
    @Serializable
    data class SeedId(
        @SerialName("seedId")
        val seedId: Int,
    )
}
