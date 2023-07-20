package com.teampophory.pophory.data.network.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NicknameRequest(
    @SerialName("nickname")
    val nickname: String
)
@Serializable
data class NicknameResponse(
    @SerialName("isDuplicated")
    val isDuplicated: Boolean
)