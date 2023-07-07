package com.teampophory.pophory.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    @SerialName("realName")
    val realName: String,
    @SerialName("nickname")
    val nickName: String,
    @SerialName("albumCover")
    val albumCover: Int
)

@Serializable
data class SignUpResponse(
    @SerialName("status")
    val status: Int
)