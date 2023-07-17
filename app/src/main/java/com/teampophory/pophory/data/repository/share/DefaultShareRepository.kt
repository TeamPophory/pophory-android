package com.teampophory.pophory.data.repository.share

import com.teampophory.pophory.network.ShareNetworkDataSource
import com.teampophory.pophory.network.model.ShareResponse
import javax.inject.Inject

class DefaultShareRepository @Inject constructor(
    private val retrofitShareNetwork: ShareNetworkDataSource
) : ShareRepository {
    override suspend fun getPhotos(): Result<ShareResponse> {
        return runCatching { retrofitShareNetwork.getPhotos() }
    }
}