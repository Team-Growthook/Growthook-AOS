package com.growthook.aos.domain.entity

data class Actionplan(
    val actionplanId: Int,
    val content: String,
    val isScraped: Boolean,
    val isFinished: Boolean,
)
