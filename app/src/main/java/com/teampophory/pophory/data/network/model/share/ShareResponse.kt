package com.teampophory.pophory.data.network.model.share

import com.teampophory.pophory.feature.share.model.PhotoItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShareResponse(
    @SerialName("photos")
    val photos: List<Photo>
) {
    @Serializable
    data class Photo(
        @SerialName("id")
        val id: Int,
        @SerialName("studio")
        val studio: String,
        @SerialName("takenAt")
        val takenAt: String,
        @SerialName("imageUrl")
        val imageUrl: String,
        @SerialName("width")
        val width: Int,
        @SerialName("height")
        val height: Int
    )

    fun toPhotos(): List<PhotoItem> {
        return photos.map { photo ->
            PhotoItem(
                id = photo.id,
                imageUrl = photo.imageUrl
            )
        }
    }
}