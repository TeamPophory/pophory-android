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

    fun mapPhotosToPhotoItems(): List<PhotoDetail> {
        val photoDetails = mutableListOf<PhotoDetail>()
        photos.orEmpty().forEach { photo ->
            val id = photo.id ?: return photoDetails
            val studio = photo.studio ?: return photoDetails
            val takenAt = photo.takenAt ?: return photoDetails
            val imageUrl = photo.imageUrl ?: return photoDetails
            val width = photo.width ?: return photoDetails
            val height = photo.height ?: return photoDetails
            when {
                (width > height) -> {
                    photoDetails.add(
                        PhotoDetail(id, studio, takenAt, imageUrl, width, height, OrientType.HORIZONTAL)
                    )
                }
                (width < height) -> {
                    photoDetails.add(
                        PhotoDetail(id, studio, takenAt, imageUrl, width, height, OrientType.VERTICAL)
                    )
                }
                else -> {
                    photoDetails.add(
                        PhotoDetail(id, studio, takenAt, imageUrl, width, height, OrientType.NONE)
                    )
                }
            }
        }
        return photoDetails
    }
}