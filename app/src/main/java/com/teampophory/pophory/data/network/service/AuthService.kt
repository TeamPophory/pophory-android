package com.teampophory.pophory.data.network.service

import com.teampophory.pophory.data.network.model.auth.AuthResponse
import com.teampophory.pophory.data.network.model.auth.SocialType
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthService {
    @POST("api/v1/auth")
    suspend fun login(
        @Header("Authorization") authorization: String,
        @Body socialType: SocialType
    ): AuthResponse

    @PUT("api/v1/auth")
    suspend fun withdraw()
}