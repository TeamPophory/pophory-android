package com.teampophory.pophory.data.network.model.share

import com.teampophory.pophory.share.entity.SharePhoto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SharePhotoResponse(
    @SerialName("realName")
    val realName: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("photoId")
    val photoId: Long,
    @SerialName("imageUrl")
    val imageUrl: String,
)

fun SharePhotoResponse.toSharePhoto() = SharePhoto(
    realName = realName,
    nickname = nickname,
    photoId = photoId,
    imageUrl = imageUrl,
)