package com.teampophory.pophory.data.network.model.auth

data class Token(
    val accessToken: String,
    val refreshToken: String,
)
