package com.teampophory.pophory.data.network.model.share

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AcceptShareResponse(
    @SerialName("albumId")
    val albumId: Long
)
