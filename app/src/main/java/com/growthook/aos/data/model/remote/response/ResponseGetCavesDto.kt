package com.growthook.aos.data.model.remote.response

import com.growthook.aos.domain.entity.Cave
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetCavesDto(
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<Caves>,
) {
    @Serializable
    data class Caves(
        @SerialName("caveId")
        val caveId: Int,
        @SerialName("caveName")
        val caveName: String,
        @SerialName("caveImageIndex")
        val caveImageIndex: Int?,
    )

    fun toCaves() = data.map { cave -> Cave(cave.caveId, cave.caveName, cave.caveImageIndex) }
}
