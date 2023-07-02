package com.teampophory.pophory.network.retrofit.album

import com.teampophory.pophory.network.PhotoNetworkDataSource
import com.teampophory.pophory.network.model.PhotoListResponse
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.http.GET

private interface RetrofitPhotoNetworkApi {
    @GET("photos")
    suspend fun getPhotos(): PhotoListResponse
}

private const val PoPhoryBaseUrl = ""

class RetrofitPhotoNetwork(
    jsonConverter: Converter.Factory,
    client: OkHttpClient
) : PhotoNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(PoPhoryBaseUrl)
        .addConverterFactory(jsonConverter)
        .client(client)
        .build()
        .create(RetrofitPhotoNetworkApi::class.java)

    override suspend fun getAlbums(): PhotoListResponse {
        return networkApi.getPhotos()
    }
}