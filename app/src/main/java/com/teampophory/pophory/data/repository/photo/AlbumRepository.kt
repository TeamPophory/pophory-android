package com.teampophory.pophory.data.repository.photo

import com.teampophory.pophory.data.network.model.album.PhotoListResponse

interface AlbumRepository {
    suspend fun getPhotos(id: Int): Result<PhotoListResponse>

    suspend fun deletePhoto(photoId: Long): Result<Unit>
}