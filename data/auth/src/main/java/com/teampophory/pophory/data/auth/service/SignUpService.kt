package com.teampophory.pophory.data.auth.service

import com.teampophory.pophory.data.auth.model.NicknameRequest
import com.teampophory.pophory.data.auth.model.NicknameResponse
import com.teampophory.pophory.data.auth.model.SignUpRequest
import com.teampophory.pophory.data.auth.model.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface SignUpService {
    @PATCH("api/v1/member")
    fun signUp(
        @Body request: SignUpRequest
    ): Call<SignUpResponse>

    @POST("api/v1/member")
    fun nicknameCheck(
        @Body request: NicknameRequest
    ): Call<NicknameResponse>
}
