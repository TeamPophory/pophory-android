package com.teampophory.pophory.network.model

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