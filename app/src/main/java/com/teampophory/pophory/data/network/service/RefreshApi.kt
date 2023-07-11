package com.teampophory.pophory.data.network.service

import com.teampophory.pophory.data.model.auth.Token
import retrofit2.http.POST

interface RefreshApi {
    @POST("api/v1/auth/token")
    suspend fun refreshToken(): Token
}
