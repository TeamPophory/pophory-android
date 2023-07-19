package com.teampophory.pophory.data.repository.share

import com.teampophory.pophory.data.network.ShareNetworkDataSource
import com.teampophory.pophory.data.network.model.share.ShareResponse
import com.teampophory.pophory.data.network.service.RetrofitShareNetworkApi
import javax.inject.Inject

class DefaultShareRepository @Inject constructor(
    private val shareNetworkDataSource: ShareNetworkDataSource
) : ShareRepository {
    override suspend fun getPhotos(): Result<ShareResponse> {
        return runCatching { shareNetworkDataSource.getPhotos() }
    }
}
