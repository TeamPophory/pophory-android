package com.teampophory.pophory.ad.service

import com.teampophory.pophory.ad.model.AdConstantResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AdService {
    @GET("/api/v2/ad")
    suspend fun getAdConstant(
        @Query("os") os: String,
        @Query("version") version: String,
    ): List<AdConstantResponse>
}
