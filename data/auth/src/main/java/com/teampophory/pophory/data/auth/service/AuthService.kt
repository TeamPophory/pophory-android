package com.teampophory.pophory.data.auth.service

import com.teampophory.pophory.data.auth.model.AuthResponse
import com.teampophory.pophory.data.auth.model.NicknameRequest
import com.teampophory.pophory.data.auth.model.NicknameResponse
import com.teampophory.pophory.data.auth.model.SignUpRequest
import com.teampophory.pophory.data.auth.model.SignUpResponse
import com.teampophory.pophory.data.auth.model.SocialType
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthService {
    @POST("api/v2/auth")
    suspend fun login(
        @Header("Authorization") authorization: String,
        @Body socialType: SocialType,
    ): AuthResponse

    @DELETE("api/v2/auth")
    suspend fun withdraw(@Header("Authorization") authorization: String): Response<Unit>

    @PATCH("api/v2/member")
    suspend fun signUp(@Body request: SignUpRequest): SignUpResponse

    @POST("api/v2/member")
    suspend fun nicknameCheck(@Body request: NicknameRequest): NicknameResponse
}
