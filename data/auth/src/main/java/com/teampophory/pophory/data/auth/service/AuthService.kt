package com.teampophory.pophory.data.auth.service

import com.teampophory.pophory.data.auth.model.AuthResponse
import com.teampophory.pophory.data.auth.model.SocialType
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("api/v1/auth")
    suspend fun login(
        @Header("Authorization") authorization: String,
        @Body socialType: SocialType,
    ): AuthResponse

    @DELETE("api/v1/auth")
    suspend fun withdraw(
        @Header("Authorization") authorization: String,
    ): Response<Unit>
}
