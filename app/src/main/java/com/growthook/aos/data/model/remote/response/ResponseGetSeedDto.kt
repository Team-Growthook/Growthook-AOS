package com.growthook.aos.data.model.remote.response

import com.growthook.aos.domain.entity.Seed
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
        val memo: String? = null,
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

    fun toSeedDetail() = Seed(
        caveName = data.caveName,
        title = data.insight,
        content = data.memo,
        source = data.source,
        date = data.lockDate,
        url = data.url,
        isScraped = data.isScraped,
        remainingDays = data.remainingDays,
    )
}
