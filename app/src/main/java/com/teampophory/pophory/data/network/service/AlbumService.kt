package com.teampophory.pophory.data.network.service

import com.teampophory.pophory.data.network.model.album.PhotoListResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumService {
    @GET("api/v1/albums/{albumId}/photos")
    suspend fun getPhotos(
        @Path("albumId") albumId: Int
    ): PhotoListResponse

    @DELETE("/api/v1/photo/{photoId}")
    suspend fun deletePhoto(
        @Path("photoId") photoId: Long
    ): Unit
}