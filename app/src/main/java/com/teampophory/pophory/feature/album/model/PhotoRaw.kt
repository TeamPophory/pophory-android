package com.teampophory.pophory.feature.album.model

data class PhotoRaw(
    val id: Long,
    val studio: String,
    val takenAt: String,
    val imageUrl: String,
    val shareId: String,
)
