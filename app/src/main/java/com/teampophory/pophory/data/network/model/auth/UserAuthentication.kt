package com.teampophory.pophory.data.network.model.auth

data class UserAuthentication(
    val token: Token,
    val isRegistered: Boolean,
)
