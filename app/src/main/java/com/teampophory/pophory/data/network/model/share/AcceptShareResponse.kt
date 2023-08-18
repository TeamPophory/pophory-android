package com.teampophory.pophory.data.network.model.share

import com.teampophory.pophory.share.entity.AcceptShare
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AcceptShareResponse(
    @SerialName("albumId")
    val albumId: Long
)

fun AcceptShareResponse.toAcceptShare() = AcceptShare(
    albumId = albumId,
)
