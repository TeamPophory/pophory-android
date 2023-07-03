package com.teampophory.pophory.data.repository

import com.teampophory.pophory.network.model.PhotoListResponse

interface PhotoRepository {
    suspend fun getPhotos(): Result<PhotoListResponse>
}