package com.teampophory.pophory.data.repository.store

import com.teampophory.pophory.network.model.StoreResponse

interface StoreRepository {
    suspend fun getAlbums(): Result<StoreResponse>
}