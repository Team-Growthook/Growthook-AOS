package com.growthook.aos.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePostAuthDto(
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: LoginInfo,
) {
    @Serializable
    data class LoginInfo(
        @SerialName("nickname")
        val nickname: String,
        @SerialName("memberId")
        val memberId: Int,
        @SerialName("accessToken")
        val accessToken: String,
        @SerialName("refreshToken")
        val refreshToken: String,
    )
}
