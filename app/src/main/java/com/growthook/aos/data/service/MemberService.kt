package com.growthook.aos.data.service

import com.growthook.aos.data.model.response.ResponseGatheredThookDto
import com.growthook.aos.data.model.response.ResponseGetUsedThook
import retrofit2.http.GET
import retrofit2.http.Path

interface MemberService {
    @GET("api/v1/member/{memberId}/gathered-ssuk")
    suspend fun getGatheredThook(
        @Path("memberId") memberId: Int,
    ): ResponseGatheredThookDto

    @GET("api/v1/memberId/{memberId}/used-ssuk")
    suspend fun getUsedThook(
        @Path("memberId") memberId: Int,
    ): ResponseGetUsedThook
}
