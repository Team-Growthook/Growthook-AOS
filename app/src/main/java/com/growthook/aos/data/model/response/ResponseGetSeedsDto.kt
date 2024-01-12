package com.growthook.aos.data.model.response

import com.growthook.aos.domain.entity.Insight
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetSeedsDto(
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<Seed>,
) {
    @Serializable
    data class Seed(
        @SerialName("seedId")
        val seedId: Int,
        @SerialName("insight")
        val insight: String,
        @SerialName("remainingDays")
        val remainingDays: Int,
        @SerialName("isLocked")
        val isLocked: Boolean,
        @SerialName("isScraped")
        val isScraped: Boolean,
        @SerialName("hasActionPlan")
        val hasActionPlan: Boolean,
    )

    fun toInsight() = data.map {
        Insight(
            it.seedId,
            it.insight,
            it.remainingDays,
            it.isLocked,
            it.isScraped,
            it.hasActionPlan,
        )
    }
}
