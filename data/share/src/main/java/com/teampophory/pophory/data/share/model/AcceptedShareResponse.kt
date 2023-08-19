package com.teampophory.pophory.data.share.model

import com.teampophory.pophory.share.entity.AcceptedSharePhoto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AcceptedShareResponse(
    @SerialName("albumId")
    val albumId: Long
)

fun AcceptedShareResponse.toAcceptShare() = AcceptedSharePhoto(
    albumId = albumId,
)
