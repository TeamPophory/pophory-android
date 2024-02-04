package com.teampophory.pophory.data.share.service

import com.teampophory.pophory.data.share.model.ShareResponse

interface ShareNetworkDataSource {
    suspend fun getPhotos(): ShareResponse
}
