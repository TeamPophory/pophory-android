package com.teampophory.pophory.feature.album.model

sealed class PhotoItem {
    data class VerticalItem(val photoDetails: List<PhotoDetail>) : PhotoItem()

    data class HorizontalItem(val photoDetail: PhotoDetail) : PhotoItem()
}

data class PhotoDetail(
    val id: Long,
    val studio: String,
    val takenAt: String,
    val imageUrl: String,
    val width: Int,
    val height: Int,
    val orientType: OrientType,
    val shareId: String
)

fun List<PhotoItem>.mapPhotoItemsToPhotoDetails(): List<PhotoDetail> {
    return flatMap { photoItem ->
        when (photoItem) {
            is PhotoItem.VerticalItem -> photoItem.photoDetails.filter { it.orientType == OrientType.VERTICAL }
            is PhotoItem.HorizontalItem -> listOf(photoItem.photoDetail)
        }
    }
}


enum class OrientType {
    VERTICAL,
    HORIZONTAL,
    NONE
}
