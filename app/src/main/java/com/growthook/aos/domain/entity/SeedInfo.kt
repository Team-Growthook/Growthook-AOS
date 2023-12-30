package com.growthook.aos.domain.entity

data class SeedInfo (
    val insight: String = "",
    val memo: String?,
    val cave: String = "",
    val source: String = "",
    val url: String?,
    val goalMonth: Int = 0
)