package com.teampophory.pophory.network.retrofit.signup

import com.teampophory.pophory.network.model.NicknameRequest
import com.teampophory.pophory.network.model.NicknameResponse
import com.teampophory.pophory.network.model.SignUpRequest
import com.teampophory.pophory.network.model.SignUpResponse
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
