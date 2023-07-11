package com.teampophory.pophory.data.network.service

import com.teampophory.pophory.data.model.auth.Token
import retrofit2.http.Header
import retrofit2.http.POST

interface RefreshApi {
    // Header에 어떤 key, value가 들어가야하는 지 미지수
    @POST("api/v1/auth/token")
    suspend fun refreshToken(@Header("") token: String): Token
}
