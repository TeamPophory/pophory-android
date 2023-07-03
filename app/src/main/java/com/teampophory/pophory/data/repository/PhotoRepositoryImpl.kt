package com.teampophory.pophory.data.repository

import com.teampophory.pophory.network.model.PhotoListResponse
import com.teampophory.pophory.network.retrofit.album.RetrofitPhotoNetwork
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val retrofitPhotoNetwork: RetrofitPhotoNetwork
) : PhotoRepository {
    override suspend fun getPhotos(): Result<PhotoListResponse> {
        return runCatching {retrofitPhotoNetwork.getAlbums()}
    }

}