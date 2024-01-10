package com.growthook.aos.data.datasource.remote

import com.growthook.aos.data.model.request.RequestCaveModifyDto
import com.growthook.aos.data.model.request.RequestCavePostDto
import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.model.response.ResponseGetCaveSeedsDto
import com.growthook.aos.data.model.response.ResponseGetCavesDto
import com.growthook.aos.data.model.response.ResponseGetDetailCaveDto
import com.growthook.aos.data.model.response.ResponsePostCaveDto

interface CaveDataSource {
    suspend fun deleteCave(caveId: Int): ResponseDto

    suspend fun getCaves(memberId: Int): ResponseGetCavesDto

    suspend fun getCaveDetail(memberId: Int, caveId: Int): ResponseGetDetailCaveDto

    suspend fun modifyCave(caveId: Int, request: RequestCaveModifyDto): ResponseDto

    suspend fun postCave(memberId: Int, request: RequestCavePostDto): ResponsePostCaveDto

    suspend fun getCaveSeeds(caveId: Int): ResponseGetCaveSeedsDto
}
