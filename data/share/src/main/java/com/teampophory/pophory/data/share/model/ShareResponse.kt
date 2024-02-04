package com.teampophory.pophory.data.share.model

import com.teampophory.pophory.share.entity.Photo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShareResponse(
    @SerialName("photos")
    val photoDetails: List<PhotoDetail>,
) {
    @Serializable
    data class PhotoDetail(
        @SerialName("photoId")
        val photoId: Long,
        @SerialName("photoUrl")
        val photoUrl: String,
        @SerialName("shareId")
        val shareId: String,
    )

    fun toPhotos(): List<Photo> {
        return photoDetails.map { photo ->
            Photo(
                photoId = photo.photoId,
                photoUrl = photo.photoUrl,
                shareId = photo.shareId,
            )
        }
    }
}
