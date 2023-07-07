package com.teampophory.pophory.network

import com.teampophory.pophory.data.network.model.album.PhotoListResponse

interface PhotoNetworkDataSource {
    suspend fun getAlbums(): PhotoListResponse
}