package com.teampophory.pophory.data.network.model.photo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoRequest(
    @SerialName("albumId")
    val albumId: Int,
    @SerialName("takenAt")
    val takenAt: String,
    @SerialName("studioId")
    val studioId: Long,
    @SerialName("fileName")
    val fileName: String,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int
)