package com.growthook.aos.data.model.response

import com.growthook.aos.domain.entity.Review
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetReviewDto(
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: ReviewData,
) {
    @Serializable
    data class ReviewData(
        @SerialName("actionPlan")
        val actionPlan: String,
        @SerialName("isScraped")
        val isScraped: Boolean,
        @SerialName("content")
        val content: String,
        @SerialName("reviewDate")
        val reviewDate: String,
    )

    fun toReview() = Review(
        actionPlan = data.actionPlan,
        isScraped = data.isScraped,
        content = data.content,
        reviewDate = data.reviewDate,
    )
}
