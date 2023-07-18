package com.teampophory.pophory.data.network.service

import com.teampophory.pophory.data.network.ShareNetworkDataSource
import com.teampophory.pophory.data.network.model.share.ShareResponse
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

interface RetrofitShareNetworkApi {
    @GET("api/v2/albums/{albumId}/photo")
    suspend fun getPhoto(
        @Path(value = "albumId") albumId: Int = 1
    ): ShareResponse
}

class RetrofitShareNetwork @Inject constructor(
    private val networkApi: RetrofitShareNetworkApi
) : ShareNetworkDataSource {
    override suspend fun getPhotos(): ShareResponse {
        return networkApi.getPhoto()
    }
}


