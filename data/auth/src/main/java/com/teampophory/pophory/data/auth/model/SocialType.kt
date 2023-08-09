package com.teampophory.pophory.data.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocialType(
    @SerialName("socialType")
    val socialType: String
)
