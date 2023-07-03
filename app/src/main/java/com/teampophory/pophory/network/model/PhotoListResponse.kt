package com.teampophory.pophory.network.model

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
        val id: Long? = null,
        @SerialName("studio")
        val studio: String? = null,
        @SerialName("takenAt")
        val takenAt: String? = null,
        @SerialName("imageUrl")
        val imageUrl: String? = null
    )
}

fun PhotoListResponse.toPhotoList(): List<PhotoItem> {
    //TODO 추후 리팩터링 예정
    return listOf(
        PhotoItem.VerticalItem(
            Pair(
                PhotoItem.VerticalItem.Photo(
                    id = photos?.getOrNull(0)?.id ?: 0,
                    studio = photos?.getOrNull(0)?.studio ?: "",
                    takenAt = photos?.getOrNull(0)?.takenAt ?: "",
                    imageUrl = photos?.getOrNull(0)?.imageUrl ?: ""
                ), PhotoItem.VerticalItem.Photo(
                    id = photos?.getOrNull(1)?.id ?: 0,
                    studio = photos?.getOrNull(1)?.studio ?: "",
                    takenAt = photos?.getOrNull(1)?.takenAt ?: "",
                    imageUrl = photos?.getOrNull(1)?.imageUrl ?: ""
                )
            )
        ), PhotoItem.VerticalItem(
            Pair(
                PhotoItem.VerticalItem.Photo(
                    id = photos?.getOrNull(0)?.id ?: 0,
                    studio = photos?.getOrNull(0)?.studio ?: "",
                    takenAt = photos?.getOrNull(0)?.takenAt ?: "",
                    imageUrl = photos?.getOrNull(0)?.imageUrl ?: ""
                ), PhotoItem.VerticalItem.Photo(
                    id = photos?.getOrNull(1)?.id ?: 0,
                    studio = photos?.getOrNull(1)?.studio ?: "",
                    takenAt = photos?.getOrNull(1)?.takenAt ?: "",
                    imageUrl = photos?.getOrNull(1)?.imageUrl ?: ""
                )
            )
        ), PhotoItem.HorizontalItem(
            id = photos?.getOrNull(0)?.id ?: 0,
            studio = photos?.getOrNull(0)?.studio ?: "",
            takenAt = photos?.getOrNull(0)?.takenAt ?: "",
            imageUrl = photos?.getOrNull(0)?.imageUrl ?: ""
        )
    )
}