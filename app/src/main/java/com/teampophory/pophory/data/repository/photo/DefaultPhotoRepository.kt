package com.teampophory.pophory.data.repository.photo

import com.teampophory.pophory.common.image.ContentUriRequestBody
import com.teampophory.pophory.data.model.photo.Studio
import com.teampophory.pophory.data.network.model.album.PhotoListResponse
import com.teampophory.pophory.data.network.service.PhotoService
import com.teampophory.pophory.network.PhotoNetworkDataSource
import javax.inject.Inject

class DefaultPhotoRepository @Inject constructor(
    private val photoService: PhotoService,
    private val photoNetworkDataSource: PhotoNetworkDataSource
) : PhotoRepository {
    override suspend fun getPhotos(id: Int): Result<PhotoListResponse> {
        return runCatching { photoService.getPhotos(id) }
    }

    override suspend fun deletePhoto(photoId: Long): Result<Unit> {
        return runCatching { photoNetworkDataSource.deletePhoto(photoId) }
    }

    override suspend fun getStudios(): Result<List<Studio>> {
        return runCatching { photoService.fetchStudios().toStudios() }
    }

    override suspend fun addPhoto(
        albumId: Int,
        takenAt: String,
        studioId: Long,
        photo: ContentUriRequestBody
    ): Result<Unit> {
        return runCatching {
            photoNetworkDataSource.addPhoto(albumId, takenAt, studioId, photo)
        }
    }
}