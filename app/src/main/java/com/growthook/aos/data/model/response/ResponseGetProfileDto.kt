package com.growthook.aos.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetProfileDto(
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: Profile,
) {
    @Serializable
    data class Profile(
        @SerialName("nickname")
        val nickName: String,
        @SerialName("email")
        val email: String,
    )
}
