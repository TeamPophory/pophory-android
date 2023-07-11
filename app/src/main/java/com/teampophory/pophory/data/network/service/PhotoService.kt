package com.teampophory.pophory.data.network.service

import com.teampophory.pophory.data.network.model.album.PhotoListResponse
import com.teampophory.pophory.data.network.model.photo.StudioResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface PhotoService {

    @GET("api/v1/studios")
    suspend fun fetchStudios(): StudioResponse

    @Multipart
    @POST("api/v1/photo")
    suspend fun addPhoto(
        @Part photo: MultipartBody.Part,
        @PartMap request: HashMap<String, RequestBody>
    ): Response<Unit>

    @GET("api/v1/albums/{albumId}/photos")
    suspend fun getPhotos(
        @Path("albumId") albumId: Int
    ): PhotoListResponse

    @DELETE("/api/v1/photo/{photoId}")
    suspend fun deletePhoto(
        @Path("photoId") photoId: Long
    ): Response<Unit>
}