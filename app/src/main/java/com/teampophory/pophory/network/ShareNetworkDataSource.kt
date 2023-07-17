package com.teampophory.pophory.network

import com.teampophory.pophory.network.model.ShareResponse

interface ShareNetworkDataSource {
    suspend fun getPhotos(): ShareResponse
}