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

private const val PoPhoryBaseUrl = BuildConfig.POPHORY_BASE_URL

class RetrofitPhotoNetwork @Inject constructor(
    jsonConverter: Converter.Factory,
    client: OkHttpClient
) : PhotoNetworkDataSource {

    private val networkApi: RetrofitPhotoNetworkApi = Retrofit.Builder()
        .baseUrl(PoPhoryBaseUrl)
        .addConverterFactory(jsonConverter)
        .client(client)
        .build()
        .create()

    override suspend fun getAlbums(): PhotoListResponse {
        return networkApi.getPhotos(2)
    }
}