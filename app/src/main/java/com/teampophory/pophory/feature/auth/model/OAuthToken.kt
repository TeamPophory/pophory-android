package com.teampophory.pophory.feature.auth.model

import com.teampophory.pophory.auth.entity.KakaoOAuthToken

typealias KakaoToken = com.kakao.sdk.auth.model.OAuthToken

fun KakaoToken.toOAuthToken() = KakaoOAuthToken(
    accessToken = accessToken,
    refreshToken = refreshToken,
)
