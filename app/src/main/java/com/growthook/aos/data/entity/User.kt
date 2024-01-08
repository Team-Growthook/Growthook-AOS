package com.growthook.aos.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String? = null,
    val memberId: Int? = null,
    val isOnboarding: Boolean = false,
)
