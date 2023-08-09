package com.teampophory.pophory.auth.entity

sealed interface OAuthToken {
    val accessToken: String
}

data class KakaoOAuthToken(
    override val accessToken: String,
    val refreshToken: String,
) : OAuthToken
