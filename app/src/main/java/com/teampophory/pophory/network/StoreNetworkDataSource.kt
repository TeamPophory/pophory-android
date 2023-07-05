package com.teampophory.pophory.network

import com.teampophory.pophory.network.model.StoreResponse

interface StoreNetworkDataSource {
    suspend fun getAlbums(): StoreResponse
}
