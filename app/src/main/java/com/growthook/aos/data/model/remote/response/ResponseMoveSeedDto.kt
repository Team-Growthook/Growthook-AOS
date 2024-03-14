package com.growthook.aos.data.model.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMoveSeedDto(
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: CaveDto,
) {

    @Serializable
    data class CaveDto(
        @SerialName("caveId")
        val caveId: Int,
        @SerialName("caveName")
        val caveName: String,
    )
}
