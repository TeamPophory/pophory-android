package com.teampophory.pophory.network.retrofit.album

import com.teampophory.pophory.data.network.model.photo.StudioResponse
import com.teampophory.pophory.network.PhotoNetworkDataSource
import com.teampophory.pophory.network.model.PhotoListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

interface RetrofitPhotoNetworkApi {
    @GET("api/v1/albums/{albumId}/photos")
    suspend fun getPhotos(
        @Path("albumId") albumId: Int
    ): PhotoListResponse

    @GET("api/v1/studios")
    suspend fun fetchStudios(): StudioResponse
}

class RetrofitPhotoNetwork @Inject constructor(
    private val networkApi: RetrofitPhotoNetworkApi
) : PhotoNetworkDataSource {
    override suspend fun getAlbums(): PhotoListResponse {
        return networkApi.getPhotos(2)
    }

    override suspend fun getStudios(): StudioResponse {
        return networkApi.fetchStudios()
    }
}
