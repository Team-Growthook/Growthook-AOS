package com.growthook.aos.domain.entity

data class Seed(
    val caveName: String,
    val title: String,
    val content: String? = null,
    val source: String,
    val date: String,
    val url: String,
    val isScraped: Boolean = false,
    val remainingDays: Int,
)
