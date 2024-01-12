package com.growthook.aos.data.service

import com.growthook.aos.data.model.response.ResponseGatheredThookDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MemberService {
    @GET("api/v1/member/{memberId}/gathered-ssuk")
    suspend fun getGatheredThook(
        @Path("memberId") memberId: Int,
    ): ResponseGatheredThookDto
}
