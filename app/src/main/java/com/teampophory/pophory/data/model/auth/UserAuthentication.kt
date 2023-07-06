package com.teampophory.pophory.data.model.auth

data class UserAuthentication(
    val token: Token,
    val isRegistered: Boolean,
)
