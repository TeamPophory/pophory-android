package com.teampophory.pophory.feature.album.model

sealed class PhotoItem {
    data class VerticalItem(val photoDetails: List<PhotoDetail>) : PhotoItem()

    data class HorizontalItem(val photoDetail: PhotoDetail) : PhotoItem()
}

data class PhotoDetail(
        val id: Int,
        val studio: String,
        val takenAt: String,
        val imageUrl: String,
        val width: Int,
        val height: Int,
        val orientType: OrientType
)

enum class OrientType {
    VERTICAL,
    HORIZONTAL,
    NONE
}
