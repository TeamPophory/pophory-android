package com.teampophory.pophory.data.share.service

import com.teampophory.pophory.data.share.model.AcceptedShareResponse
import com.teampophory.pophory.data.share.model.SharePhotoResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ShareService {
    @GET("api/v2/share/{shareId}")
    suspend fun getPhotoInfo(
        @Path("shareId") shareId: String,
    ): SharePhotoResponse

    @POST("api/v2/share/photo/{photoId}")
    suspend fun acceptShare(
        @Path("photoId") photoId: Long,
    ): AcceptedShareResponse
}
