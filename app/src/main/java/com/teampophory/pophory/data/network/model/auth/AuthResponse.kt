package com.teampophory.pophory.data.network.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("isRegistered")
    val isRegistered: Boolean
) {
    fun toUserAuthentication() = UserAuthentication(
        token = Token(accessToken, refreshToken),
        isRegistered = isRegistered
    )
}
