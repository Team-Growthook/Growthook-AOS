package com.growthook.aos.data.model.response

import com.growthook.aos.domain.entity.Cave
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetCavesDto(
    @SerialName("caveId")
    val caveId: Int,
    @SerialName("caveName")
    val caveName: String,
) {
    fun toCave() = Cave(caveId, caveName)
}
