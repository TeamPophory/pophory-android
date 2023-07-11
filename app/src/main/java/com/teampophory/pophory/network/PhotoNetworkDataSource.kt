package com.teampophory.pophory.network

import com.teampophory.pophory.common.image.ContentUriRequestBody

interface PhotoNetworkDataSource {

    suspend fun addPhoto(
        albumId: Int,
        takenAt: String,
        studioId: Long,
        photo: ContentUriRequestBody
    ): Unit?

    suspend fun deletePhoto(
        photoId: Long
    ): Unit?
}