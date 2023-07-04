package com.teampophory.pophory.network.retrofit.album

import com.teampophory.pophory.BuildConfig
import com.teampophory.pophory.network.PhotoNetworkDataSource
import com.teampophory.pophory.network.model.PhotoListResponse
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

private interface RetrofitPhotoNetworkApi {
    @GET("api/v1/albums/{albumId}/photos")
    suspend fun getPhotos(
        @Path("albumId") albumId: Int
    ): PhotoListResponse
}

class RetrofitPhotoNetwork @Inject constructor(
    retrofit: Retrofit
) : PhotoNetworkDataSource {
    private val networkApi: RetrofitPhotoNetworkApi = retrofit.create()

    override suspend fun getAlbums(): PhotoListResponse {
        return networkApi.getPhotos(2)
    }
}