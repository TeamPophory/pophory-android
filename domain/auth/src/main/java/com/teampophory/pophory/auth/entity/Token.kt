package com.teampophory.pophory.auth.entity

data class Token(
    val accessToken: String,
    val refreshToken: String,
)
