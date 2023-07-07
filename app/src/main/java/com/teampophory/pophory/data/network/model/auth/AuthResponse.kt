package com.teampophory.pophory.data.network.model.auth

import com.teampophory.pophory.data.model.auth.Token
import com.teampophory.pophory.data.model.auth.UserAuthentication
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("registered")
    val isRegistered: Boolean
) {
    fun toUserAuthentication() = UserAuthentication(
        token = Token(accessToken, refreshToken),
        isRegistered = isRegistered
    )
}
