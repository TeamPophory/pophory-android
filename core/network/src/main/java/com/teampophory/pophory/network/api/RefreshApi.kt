package com.teampophory.pophory.network.api

import com.teampophory.pophory.network.model.TokenResponse
import retrofit2.http.Header
import retrofit2.http.POST

interface RefreshApi {
    @POST("api/v1/auth/token")
    suspend fun refreshToken(@Header("Authorization") token: String): TokenResponse
}
