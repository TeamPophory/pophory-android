package com.teampophory.pophory.auth.entity

data class UserAuthentication(
    val token: Token,
    val isRegistered: Boolean,
)
