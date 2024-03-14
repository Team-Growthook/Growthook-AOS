package com.growthook.aos.data.model.remote.response

import com.growthook.aos.domain.entity.Profile
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
    val data: ProfileInfo,
) {
    @Serializable
    data class ProfileInfo(
        @SerialName("nickname")
        val nickName: String,
        @SerialName("email")
        val email: String,
        @SerialName("profileImage")
        val profileImage: String?,
    )

    fun toProfile() = Profile(data.email, data.profileImage)
}
