package com.teampophory.pophory.feature.share.model

import com.teampophory.pophory.share.entity.Photo

data class PhotoItem(
    val photoId: Long,
    val photoUrl: String,
    val shareId: String,
)

fun Photo.toPhotoItem() = PhotoItem(
    photoId = photoId,
    photoUrl = photoUrl,
    shareId = shareId,
)

fun List<Photo>.toPhotoItems() = map { it.toPhotoItem() }