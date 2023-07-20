package com.teampophory.pophory.data.network.service

import com.teampophory.pophory.data.network.model.share.AcceptShareResponse
import com.teampophory.pophory.data.network.model.share.SharePhotoResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ShareService {
    @GET("api/v2/share/{shareId}")
    suspend fun getPhotoInfo(
        @Path("shareId") shareId: String
    ): SharePhotoResponse

    @POST("api/v2/share/photo/{photoId}")
    suspend fun acceptShare(
        @Path("photoId") photoId: Long
    ): AcceptShareResponse
}
