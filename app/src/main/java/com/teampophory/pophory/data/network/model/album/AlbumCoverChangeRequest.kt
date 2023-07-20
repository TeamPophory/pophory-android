package com.teampophory.pophory.data.network.model.album

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumCoverChangeRequest(
    @SerialName("albumDesignId")
    private val albumDesignId: Long
)