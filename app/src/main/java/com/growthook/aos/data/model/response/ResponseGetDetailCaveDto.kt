package com.growthook.aos.data.model.response

import com.growthook.aos.domain.entity.CaveDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetDetailCaveDto(
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: DetailCave,
) {
    @Serializable
    data class DetailCave(
        @SerialName("caveName")
        val caveName: String,
        @SerialName("introduction")
        val introduction: String,
        @SerialName("nickname")
        val nickname: String,
        @SerialName("isShared")
        val isShared: Boolean,
    )

    fun toCaveDetail() = CaveDetail(data.caveName, data.introduction, data.nickname, data.isShared)
}
