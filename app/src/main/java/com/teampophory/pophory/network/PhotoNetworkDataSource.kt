package com.teampophory.pophory.network

import com.teampophory.pophory.data.network.model.photo.StudioResponse
import com.teampophory.pophory.network.model.PhotoListResponse

interface PhotoNetworkDataSource {
    suspend fun getAlbums(): PhotoListResponse
    suspend fun getStudios(): StudioResponse
}