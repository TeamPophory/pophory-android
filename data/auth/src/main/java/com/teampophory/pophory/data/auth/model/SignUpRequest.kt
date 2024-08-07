package com.teampophory.pophory.data.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    @SerialName("realName")
    val realName: String,
    @SerialName("nickname")
    val nickName: String,
    @SerialName("albumCover")
    val albumCover: Int,
    @SerialName("fcmToken")
    val fcmToken: String? = null,
    @SerialName("fcmOS")
    val profileImage: String? = null
)

@Serializable
data class SignUpResponse(
    @SerialName("albumId")
    val albumId: Int,
)
