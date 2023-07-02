package com.teampophory.pophory.network.model

import com.teampophory.pophory.feature.album.model.PhotoList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoListResponse(
    @SerialName("photos")
    val photos: List<Photo>? = null
) {
    @Serializable
    data class Photo(
        @SerialName("id")
        val id: Long? = null,
        @SerialName("studio")
        val studio: String? = null,
        @SerialName("takenAt")
        val takenAt: String? = null,
        @SerialName("imageUrl")
        val imageUrl: String? = null
    )
}

fun PhotoListResponse.toPhotoList(): PhotoList {
    return PhotoList(
        photos = photos?.map {
            PhotoList.Photo(
                id = it.id ?: 0,
                studio = it.studio ?: "",
                takenAt = it.takenAt ?: "",
                imageUrl = it.imageUrl ?: ""
            )
        } ?: emptyList()
    )
}