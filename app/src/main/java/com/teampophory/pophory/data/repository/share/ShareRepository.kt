package com.teampophory.pophory.data.repository.share

import com.teampophory.pophory.network.model.ShareResponse

interface ShareRepository {
    suspend fun getPhotos(): Result<ShareResponse>
}
