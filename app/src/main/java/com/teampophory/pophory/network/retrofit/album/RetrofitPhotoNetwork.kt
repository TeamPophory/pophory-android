package com.teampophory.pophory.network.retrofit.album

import com.teampophory.pophory.common.image.ContentUriRequestBody
import com.teampophory.pophory.common.okhttp.toPlainRequestBody
import com.teampophory.pophory.data.network.model.photo.StudioResponse
import com.teampophory.pophory.network.PhotoNetworkDataSource
import com.teampophory.pophory.network.model.PhotoListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import javax.inject.Inject

interface RetrofitPhotoNetworkApi {
    @GET("api/v1/albums/{albumId}/photos")
    suspend fun getPhotos(
        @Path("albumId") albumId: Int
    ): PhotoListResponse

    @GET("api/v1/studios")
    suspend fun fetchStudios(): StudioResponse

    @Multipart
    @POST("api/v1/photo")
    suspend fun addPhoto(
        @Part photo: MultipartBody.Part,
        @PartMap request: HashMap<String, RequestBody>
    )
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

    override suspend fun addPhoto(
        albumId: Int,
        takenAt: String,
        studioId: Int,
        photo: ContentUriRequestBody
    ) {
        val file = photo.toFormData("photo")
        val request = HashMap<String, RequestBody>().apply {
            put("albumId", albumId.toString().toPlainRequestBody())
            put("takenAt", takenAt.toPlainRequestBody())
            put("studioId", studioId.toString().toPlainRequestBody())
        }
        return networkApi.addPhoto(file, request)
    }
}
