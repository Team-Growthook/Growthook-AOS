package com.growthook.aos.data.model.response

import com.growthook.aos.domain.entity.ActionlistDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseActionlistDto(
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<Actionlist>,
) {
    @Serializable
    data class Actionlist(
        @SerialName("actionPlanId")
        val actionPlanId: Int,
        @SerialName("content")
        val content: String,
        @SerialName("isScraped")
        val isScraped: Boolean,
        @SerialName("seedId")
        val seedId: Int,
        @SerialName("hasReview")
        val hasReview: Boolean,
    )

    fun toActionlist() = data.map { actionplan ->
        ActionlistDetail(
            actionplan.actionPlanId,
            actionplan.content,
            actionplan.isScraped,
            actionplan.seedId,
            actionplan.hasReview,
        )
    }
}
