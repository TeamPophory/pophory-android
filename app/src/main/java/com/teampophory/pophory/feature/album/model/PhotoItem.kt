package com.teampophory.pophory.feature.album.model

sealed class PhotoItem {
    data class VerticalItem(
        val photo: Pair<Photo, Photo>
    ) : PhotoItem() {
        data class Photo(
            val id: Long,
            val studio: String,
            val takenAt: String,
            val imageUrl: String,
        )
    }

    data class HorizontalItem(
        val id: Long,
        val studio: String,
        val takenAt: String,
        val imageUrl: String,
    ) : PhotoItem()
}
