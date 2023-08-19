package com.teampophory.pophory.data.share.service

import com.teampophory.pophory.data.share.model.ShareResponse
import retrofit2.http.GET
import javax.inject.Inject

interface RetrofitShareNetworkApi {
    @GET("api/v2/photo")
    suspend fun getPhoto(): ShareResponse
}

class RetrofitShareNetwork @Inject constructor(
    private val networkApi: RetrofitShareNetworkApi
) : ShareNetworkDataSource {
    override suspend fun getPhotos(): ShareResponse {
        return networkApi.getPhoto()
    }
}


