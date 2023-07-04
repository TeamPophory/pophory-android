package com.teampophory.pophory.feature.album.model

sealed class PhotoItem {
    data class VerticalItem(val photos: List<Photo>) : PhotoItem()

    data class HorizontalItem(val photo: Photo) : PhotoItem()
}

data class Photo(
        val id: Long,
        val studio: String,
        val takenAt: String,
        val imageUrl: String,
)
