package com.teampophory.pophory.network

import com.teampophory.pophory.network.model.PhotoListResponse

interface PhotoNetworkDataSource {
    suspend fun getAlbums(): PhotoListResponse
}