package com.growthook.aos.domain.entity

data class Insight(
    val seedId: Int,
    val name: String,
    val remainingDays: Int,
    val isLocked: Boolean,
    val isScraped: Boolean,
    val hasActionPlan: Boolean,
)
