package com.growthook.aos.data.service

import com.growthook.aos.data.model.response.ResponseDto
import com.growthook.aos.data.model.response.ResponseGatheredThookDto
import com.growthook.aos.data.model.response.ResponseGetProfileDto
import com.growthook.aos.data.model.response.ResponseGetUsedThook
import retrofit2.http.DELETE
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

    @GET("api/v1/member/{memberId}/profile")
    suspend fun getEmail(
        @Path("memberId") memberId: Int,
    ): ResponseGetProfileDto

    @DELETE("api/v1/member/{memberId}")
    suspend fun deleteMember(
        @Path("memberId") memberId: Int,
    ): ResponseDto
}
