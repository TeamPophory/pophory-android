package com.teampophory.pophory.feature.auth.model

typealias KakaoToken = com.kakao.sdk.auth.model.OAuthToken

sealed interface OAuthToken

data class KakaoOAuthToken(
    val accessToken: String,
    val refreshToken: String,
) : OAuthToken

fun KakaoToken.toOAuthToken() = KakaoOAuthToken(
    accessToken = accessToken,
    refreshToken = refreshToken,
)
