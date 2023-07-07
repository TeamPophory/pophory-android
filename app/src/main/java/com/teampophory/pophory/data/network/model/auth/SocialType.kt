package com.teampophory.pophory.data.network.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocialType(
    @SerialName("socialType")
    val socialType: String
)
