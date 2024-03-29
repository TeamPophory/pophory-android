package com.teampophory.pophory.data.network.service

import com.teampophory.pophory.data.network.model.album.AlbumCoverChangeRequest
import com.teampophory.pophory.data.network.model.album.PhotoListResponse
import com.teampophory.pophory.data.network.model.photo.PhotoRequest
import com.teampophory.pophory.data.network.model.photo.S3ImageResponse
import com.teampophory.pophory.data.network.model.photo.StudioResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Url

interface PhotoService {

    @GET("api/v1/studios")
    suspend fun fetchStudios(): StudioResponse

    @Deprecated("Use addPhotoToPophory instead")
    @Multipart
    @POST("api/v1/photo")
    suspend fun addPhoto(
        @Part photo: MultipartBody.Part,
        @PartMap request: HashMap<String, RequestBody>,
    ): Response<Unit>

    @POST("api/v2/photo")
    suspend fun addPhotoToPophory(
        @Body request: PhotoRequest,
    ): Response<Unit>

    @PUT
    suspend fun postPhotoToS3(
        @Url url: String,
        @Body photo: RequestBody,
    ): Response<Unit>

    @GET("api/v2/album/{albumId}/photo")
    suspend fun getPhotos(
        @Path("albumId") albumId: Long,
    ): PhotoListResponse

    @DELETE("api/v2/photo/{photoId}")
    suspend fun deletePhoto(
        @Path("photoId") photoId: Long,
    ): Response<Unit>

    @GET("api/v2/s3/photo")
    suspend fun getPhotoInfoFromS3(): S3ImageResponse

    @PATCH("/api/v2/album/{albumId}")
    suspend fun patchAlbumCover(
        @Path("albumId") albumId: Long,
        @Body request: AlbumCoverChangeRequest,
    ): Response<Unit>
}
