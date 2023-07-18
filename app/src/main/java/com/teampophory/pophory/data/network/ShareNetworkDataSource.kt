package com.teampophory.pophory.data.network

import com.teampophory.pophory.data.network.model.share.ShareResponse

interface ShareNetworkDataSource {
    suspend fun getPhotos(): ShareResponse
}