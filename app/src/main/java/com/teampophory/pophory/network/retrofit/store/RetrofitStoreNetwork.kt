package com.teampophory.pophory.network.retrofit.store

import com.teampophory.pophory.network.StoreNetworkDataSource
import com.teampophory.pophory.network.model.StoreResponse
import retrofit2.http.GET
import javax.inject.Inject

interface RetrofitStoreNetworkApi {
    @GET("api/v2/albums")
    suspend fun getAlbum(): StoreResponse
}

class RetrofitStoreNetwork @Inject constructor(
    private val networkApi: RetrofitStoreNetworkApi
) : StoreNetworkDataSource {
    override suspend fun getAlbums(): StoreResponse {
        return networkApi.getAlbum()
    }
}