package com.teampophory.pophory.data.repository.photo

import com.teampophory.pophory.common.image.ContentUriRequestBody
import com.teampophory.pophory.data.model.photo.Studio
import com.teampophory.pophory.data.network.model.album.PhotoListResponse

interface PhotoRepository {
    suspend fun getPhotos(id: Int): Result<PhotoListResponse>

    suspend fun deletePhoto(photoId: Long): Result<Unit>

    suspend fun getStudios(): Result<List<Studio>>

    suspend fun addPhoto(
        albumId: Int,
        takenAt: String,
        studioId: Long,
        photo: ContentUriRequestBody
    ):Result<Unit>
}