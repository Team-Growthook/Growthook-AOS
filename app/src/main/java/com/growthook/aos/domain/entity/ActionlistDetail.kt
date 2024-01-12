package com.growthook.aos.domain.entity

data class ActionlistDetail(
    val actionplanId: Int,
    val content: String,
    val isScraped: Boolean,
    val seedId: Int,
)
