package com.teampophory.pophory.data.repository.photo

import com.teampophory.pophory.common.image.ContentUriRequestBody
import com.teampophory.pophory.data.model.photo.Studio
import com.teampophory.pophory.network.model.PhotoListResponse

interface PhotoRepository {
    suspend fun getPhotos(): Result<PhotoListResponse>
    suspend fun getStudios(): Result<List<Studio>>
    suspend fun addPhoto(
        albumId: Int,
        takenAt: String,
        studioId: Long,
        photo: ContentUriRequestBody
    ): Result<Unit>
}