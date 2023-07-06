package com.teampophory.pophory.data.repository.photo

import com.teampophory.pophory.data.network.model.album.PhotoListResponse
import com.teampophory.pophory.data.network.service.AlbumService
import javax.inject.Inject

class DefaultAlbumRepository @Inject constructor(
    private val networkApi: AlbumService
) : AlbumRepository {
    override suspend fun getPhotos(id:Int): Result<PhotoListResponse> {
        return runCatching { networkApi.getPhotos(id) }
    }

    override suspend fun deletePhoto(photoId: Long): Result<Unit> {
        return runCatching { networkApi.deletePhoto(photoId) }
    }

}