package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.response.ResponseGetActionplanDto

interface ActionplanDataSource {
    suspend fun getActionplans(seedId: Int): ResponseGetActionplanDto
}
