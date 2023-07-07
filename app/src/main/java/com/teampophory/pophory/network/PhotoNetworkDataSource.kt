package com.teampophory.pophory.network

import com.teampophory.pophory.common.image.ContentUriRequestBody
import com.teampophory.pophory.data.network.model.photo.StudioResponse
import com.teampophory.pophory.network.model.PhotoListResponse
import okhttp3.RequestBody

interface PhotoNetworkDataSource {
    suspend fun getAlbums(): PhotoListResponse
    suspend fun getStudios(): StudioResponse
    suspend fun addPhoto(
        albumId: Int,
        takenAt: String,
        studioId: Long,
        photo: ContentUriRequestBody
    )
}