package com.growthook.aos.domain.entity

data class Review(
    val actionPlan: String,
    val isScraped: Boolean,
    val content: String,
    val reviewDate: String,
)
