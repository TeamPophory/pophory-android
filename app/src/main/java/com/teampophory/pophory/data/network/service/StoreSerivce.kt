package com.teampophory.pophory.data.network.service

import com.teampophory.pophory.data.network.model.share.StoreResponse
import retrofit2.http.GET

interface StoreSerivce {
    @GET("api/v2/albums")
    suspend fun getAlbum(): StoreResponse
}