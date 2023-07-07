package com.teampophory.pophory.data.repository.photo

import com.teampophory.pophory.common.image.ContentUriRequestBody
import com.teampophory.pophory.data.model.photo.Studio
import com.teampophory.pophory.network.PhotoNetworkDataSource
import com.teampophory.pophory.network.model.PhotoListResponse
import javax.inject.Inject

class DefaultPhotoRepository @Inject constructor(
    private val retrofitPhotoNetwork: PhotoNetworkDataSource
) : PhotoRepository {
    override suspend fun getPhotos(): Result<PhotoListResponse> {
        return runCatching { retrofitPhotoNetwork.getAlbums() }
    }

    override suspend fun getStudios(): Result<List<Studio>> {
        return runCatching { retrofitPhotoNetwork.getStudios().toStudios() }
    }

    override suspend fun addPhoto(
        albumId: Int,
        takenAt: String,
        studioId: Long,
        photo: ContentUriRequestBody
    ): Result<Unit> {
        return runCatching {
            retrofitPhotoNetwork.addPhoto(
                albumId,
                takenAt,
                studioId,
                photo
            )
        }
    }
}
