package com.teampophory.pophory.data.repository.share

import com.teampophory.pophory.data.network.model.share.ShareResponse

interface ShareRepository {
    suspend fun getPhotos(): Result<ShareResponse>
}
