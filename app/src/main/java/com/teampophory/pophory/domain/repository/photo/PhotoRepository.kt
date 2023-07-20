package com.teampophory.pophory.domain.repository.photo

import com.teampophory.pophory.data.model.photo.Studio
import com.teampophory.pophory.data.network.model.album.AlbumCoverChangeRequest
import com.teampophory.pophory.data.network.model.album.PhotoListResponse
import com.teampophory.pophory.domain.model.S3Image
import okhttp3.RequestBody

interface PhotoRepository {
    suspend fun getPhotos(id: Long): Result<PhotoListResponse>

    suspend fun deletePhoto(photoId: Long): Result<Unit>

    suspend fun getStudios(): Result<List<Studio>>

    suspend fun addPhotoToPophory(
        albumId: Long,
        takenAt: String,
        studioId: Long,
        fileName: String,
        width: Int,
        height: Int
    ): Result<Unit>

    suspend fun getPhotoInfoFromS3(): Result<S3Image>

    suspend fun postPhotoToS3(url: String, photo: RequestBody)
    suspend fun patchAlbumCover(
        albumCoverId: Long,
        albumCoverChangeRequest: AlbumCoverChangeRequest
    ): Result<Unit>
}