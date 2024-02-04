package com.teampophory.pophory.data.network.service

import com.teampophory.pophory.data.network.model.store.StoreResponse
import retrofit2.http.GET

interface StoreSerivce {
    @GET("api/v2/album")
    suspend fun getAlbum(): StoreResponse
}
