package com.growthook.aos.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetSeedDto(
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: SeedDetail,
) {
    @Serializable
    data class SeedDetail(
        @SerialName("caveName")
        val caveName: String,
        @SerialName("insight")
        val insight: String,
        @SerialName("memo")
        val memo: String,
        @SerialName("source")
        val source: String,
        @SerialName("url")
        val url: String,
        @SerialName("isScraped")
        val isScraped: Boolean,
        @SerialName("lockDate")
        val lockDate: String,
        @SerialName("remainingDays")
        val remainingDays: Int,
    )
}
