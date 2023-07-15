package com.teampophory.pophory.data.network.model.photo

import kotlinx.serialization.Serializable

@Serializable
data class PhotoRequest(
    val albumId: Int,
    val takenAt: String,
    val studioId: Long,
    val fileName: String,
    val width: Int,
    val height: Int
)