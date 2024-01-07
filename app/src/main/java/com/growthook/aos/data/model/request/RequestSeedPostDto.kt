package com.growthook.aos.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestSeedPostDto(
    @SerialName("insight")
    val insight: String,
    @SerialName("memo")
    val memo: String,
    @SerialName("source")
    val source: String,
    @SerialName("url")
    val url: String,
    @SerialName("goalMonth")
    val goalMonth: Int
)
