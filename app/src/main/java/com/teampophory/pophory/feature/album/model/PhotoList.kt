package com.teampophory.pophory.feature.album.model

data class PhotoList(
    val photos: List<Photo>
) {
    data class Photo(
        val id: Long,
        val studio: String,
        val takenAt: String,
        val imageUrl: String
    )
}
