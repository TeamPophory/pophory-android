package com.teampophory.pophory.data.network.service

import com.teampophory.pophory.data.network.model.auth.TokenResponse
import retrofit2.http.Header
import retrofit2.http.POST

interface RefreshApi {
    @POST("api/v1/auth/token")
    suspend fun refreshToken(@Header("Authorization") token: String): TokenResponse
}
