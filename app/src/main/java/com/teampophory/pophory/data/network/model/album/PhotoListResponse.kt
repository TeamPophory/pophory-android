package com.teampophory.pophory.data.network.model.album

import com.teampophory.pophory.feature.album.model.OrientType
import com.teampophory.pophory.feature.album.model.PhotoDetail
import com.teampophory.pophory.feature.album.model.PhotoItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoListResponse(
    @SerialName("photos") val photos: List<Photo>? = null
) {
    @Serializable
    data class Photo(
        @SerialName("id")
        val id: Int? = null,
        @SerialName("studio")
        val studio: String? = null,
        @SerialName("takenAt")
        val takenAt: String? = null,
        @SerialName("imageUrl")
        val imageUrl: String? = null,
        @SerialName("width")
        val width: Int? = null,
        @SerialName("height")
        val height: Int? = null
    )

    fun mapPhotosToPhotoItems(): List<PhotoItem> {
        val photoItems = mutableListOf<PhotoItem>()
        val verticalPhotoDetails = mutableListOf<PhotoDetail>()
        photos.orEmpty().forEach { photo ->
            val id = photo.id ?: return photoItems
            val studio = photo.studio ?: return photoItems
            val takenAt = photo.takenAt ?: return photoItems
            val imageUrl = photo.imageUrl ?: return photoItems
            val width = photo.width ?: return photoItems
            val height = photo.height ?: return photoItems
            when {
                (width > height) -> {
                    photoItems.add(
                        PhotoItem.HorizontalItem(
                            PhotoDetail(id, studio, takenAt, imageUrl, width, height, OrientType.HORIZONTAL)
                        )
                    )
                }
                (width < height) -> {
                    verticalPhotoDetails.add(PhotoDetail(id, studio, takenAt, imageUrl, width, height, OrientType.VERTICAL))
                    if (verticalPhotoDetails.size == 2) {
                        photoItems.add(PhotoItem.VerticalItem(verticalPhotoDetails.toList()))
                        verticalPhotoDetails.clear()
                    }
                }
                else -> {
                    photoItems.add(
                        PhotoItem.HorizontalItem(
                            PhotoDetail(id, studio, takenAt, imageUrl, width, height, OrientType.NONE)
                        )
                    )
                }
            }
        }

        if (verticalPhotoDetails.isNotEmpty()) {
            photoItems.add(PhotoItem.VerticalItem(verticalPhotoDetails))
        }
        return photoItems
    }
}