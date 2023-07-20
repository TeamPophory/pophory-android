package com.teampophory.pophory.data.network.service

import com.teampophory.pophory.data.network.model.auth.NicknameRequest
import com.teampophory.pophory.data.network.model.auth.NicknameResponse
import com.teampophory.pophory.data.network.model.auth.SignUpRequest
import com.teampophory.pophory.data.network.model.auth.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface RetrofitSignUpNetwork {
    @PATCH("api/v1/member")
    fun signUp(
        @Body request: SignUpRequest
    ): Call<SignUpResponse>

    @POST("api/v1/member")
    fun nicknameCheck(
        @Body request: NicknameRequest
    ): Call<NicknameResponse>
}
