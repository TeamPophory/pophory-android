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
        @SerialName("photoId")
        val photoId: Long,
        @SerialName("photoUrl")
        val photoUrl: String,
        @SerialName("shareId")
        val shareId: String
    )

    fun toPhotos(): List<PhotoItem> {
        return photos.map { photo ->
            PhotoItem(
                photoId = photo.photoId,
                photoUrl = photo.photoUrl,
                shareId = photo.shareId
            )
        }
    }
}