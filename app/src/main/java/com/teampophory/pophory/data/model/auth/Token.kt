package com.teampophory.pophory.data.model.auth

data class Token(
    val accessToken: String,
    val refreshToken: String,
)
