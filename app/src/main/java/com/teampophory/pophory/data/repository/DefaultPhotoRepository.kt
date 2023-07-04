package com.teampophory.pophory.data.repository

import com.teampophory.pophory.network.PhotoNetworkDataSource
import com.teampophory.pophory.network.model.PhotoListResponse
import javax.inject.Inject

class DefaultPhotoRepository @Inject constructor(
    private val retrofitPhotoNetwork: PhotoNetworkDataSource
) : PhotoRepository {
    override suspend fun getPhotos(): Result<PhotoListResponse> {
        return runCatching { retrofitPhotoNetwork.getAlbums() }
    }

}