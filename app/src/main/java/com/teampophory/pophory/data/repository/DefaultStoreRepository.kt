package com.teampophory.pophory.data.repository

import com.teampophory.pophory.network.StoreNetworkDataSource
import com.teampophory.pophory.network.model.StoreResponse
import javax.inject.Inject

class DefaultStoreRepository @Inject constructor(
    private val retrofitStoreNetwork: StoreNetworkDataSource
) : StoreRepository {
    override suspend fun getAlbums(): Result<StoreResponse> {
        return runCatching { retrofitStoreNetwork.getAlbums() }
    }
}