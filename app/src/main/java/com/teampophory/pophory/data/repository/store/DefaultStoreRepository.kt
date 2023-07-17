package com.teampophory.pophory.data.repository.store

import com.teampophory.pophory.data.network.model.share.StoreResponse
import com.teampophory.pophory.data.network.service.StoreSerivce
import javax.inject.Inject

class DefaultStoreRepository @Inject constructor(
    private val retrofitStoreNetwork: StoreSerivce
) : StoreRepository {
    override suspend fun getAlbums(): Result<StoreResponse> {
        return runCatching { retrofitStoreNetwork.getAlbum() }
    }
}