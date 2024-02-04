package com.teampophory.pophory.data.auth.model

import com.teampophory.pophory.auth.entity.Token
import com.teampophory.pophory.auth.entity.UserAuthentication
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("isRegistered")
    val isRegistered: Boolean,
) {
    fun toUserAuthentication() = UserAuthentication(
        token = Token(accessToken, refreshToken),
        isRegistered = isRegistered,
    )
}
