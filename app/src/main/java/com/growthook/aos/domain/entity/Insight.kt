package com.growthook.aos.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Insight(
    val title: String,
    val remainedLock: Int,
    val isScraped: Boolean,
    val isLocked: Boolean,
    val isAction: Boolean,
    val insightId: Int
) : Parcelable
