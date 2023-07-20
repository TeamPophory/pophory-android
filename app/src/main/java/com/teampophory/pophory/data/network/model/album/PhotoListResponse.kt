package com.teampophory.pophory.data.network.model.album

import com.teampophory.pophory.feature.album.model.OrientType
import com.teampophory.pophory.feature.album.model.PhotoDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoListResponse(
    @SerialName("photos") val photos: List<Photo>? = null
) {
    @Serializable
    data class Photo(
        @SerialName("id")
        val id: Long,
        @SerialName("studio")
        val studio: String? = null,
        @SerialName("takenAt")
        val takenAt: String? = null,
        @SerialName("imageUrl")
        val imageUrl: String? = null,
        @SerialName("width")
        val width: Int? = null,
        @SerialName("height")
        val height: Int? = null,
        @SerialName("shareId")
        val shareId: String
    )

    fun mapPhotosToPhotoItems(): List<PhotoDetail> {
        val photoDetails = mutableListOf<PhotoDetail>()
        return photos.orEmpty().map { photo ->
            PhotoDetail(
                id = photo.id,
                studio = photo.studio ?: return photoDetails,
                takenAt = photo.takenAt ?: return photoDetails,
                imageUrl = photo.imageUrl ?: return photoDetails,
                width = photo.width ?: return photoDetails,
                height = photo.height ?: return photoDetails,
                orientType = when {
                    (photo.width >= photo.height) -> OrientType.HORIZONTAL
                    else -> OrientType.VERTICAL
                },
                shareId = photo.shareId
            )
        }
    }
}