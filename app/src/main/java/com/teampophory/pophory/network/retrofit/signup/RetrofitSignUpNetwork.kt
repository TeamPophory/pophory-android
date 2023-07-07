package com.teampophory.pophory.network.retrofit.signup

import com.teampophory.pophory.network.model.SignUpRequest
import com.teampophory.pophory.network.model.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface RetrofitSignUpNetwork {

    @PATCH("api/v1/member")
    fun signUp(
        @Body request : SignUpRequest
    ): Call<SignUpResponse>
}