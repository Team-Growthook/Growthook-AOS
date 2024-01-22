package com.growthook.aos.data.model.response

import com.growthook.aos.data.entity.Token
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRefreshTokenDto(
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: ReIssueRefresh,
) {
    @Serializable
    data class ReIssueRefresh(
        @SerialName("accessToken")
        val accessToken: String,
        @SerialName("refreshToken")
        val refreshToken: String,
    )

    fun toRefresh() = Token(data.accessToken, data.refreshToken)
}
