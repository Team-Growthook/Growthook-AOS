package com.growthook.aos.domain.entity

data class Insight(
    val title: String,
    val remainedLock: Int,
    val isScraped: Boolean,
    val isLocked: Boolean,
    val isAction: Boolean,
    val insightId: Int,
)
