package com.teampophory.pophory.domain.repository.photo

import com.teampophory.pophory.common.image.ContentUriRequestBody
import com.teampophory.pophory.data.model.photo.Studio
import com.teampophory.pophory.data.network.model.album.PhotoListResponse
import com.teampophory.pophory.domain.model.PhotoInfoFromS3

interface PhotoRepository {
    suspend fun getPhotos(id: Int): Result<PhotoListResponse>

    suspend fun deletePhoto(photoId: Int): Result<Unit>

    suspend fun getStudios(): Result<List<Studio>>

    suspend fun addPhotoToPophory(
        albumId: Int,
        takenAt: String,
        studioId: Long,
        fileName: String,
        width: Int,
        height: Int
    ): Result<Unit>

    suspend fun getPhotoInfoFromS3(): Result<PhotoInfoFromS3>

    suspend fun addPhotoToS3(
        url: String,
        photo: ContentUriRequestBody
    ): Result<Unit>
}