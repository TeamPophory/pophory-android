package com.teampophory.pophory.data.repository.store

import com.teampophory.pophory.data.network.model.store.StoreResponse

interface StoreRepository {
    suspend fun getAlbums(): Result<StoreResponse>
}