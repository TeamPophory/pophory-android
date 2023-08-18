package com.teampophory.pophory.data.repository.share

import com.teampophory.pophory.common.qualifier.Secured
import com.teampophory.pophory.common.qualifier.Unsecured
import com.teampophory.pophory.data.network.ShareNetworkDataSource
import com.teampophory.pophory.data.network.model.share.ShareResponse
import com.teampophory.pophory.data.network.model.share.toAcceptShare
import com.teampophory.pophory.data.network.model.share.toSharePhoto
import com.teampophory.pophory.data.network.service.ShareService
import com.teampophory.pophory.share.entity.AcceptShare
import com.teampophory.pophory.share.entity.Photo
import com.teampophory.pophory.share.entity.SharePhoto
import com.teampophory.pophory.share.repository.ShareRepository
import javax.inject.Inject

class DefaultShareRepository @Inject constructor(
    private val shareNetworkDataSource: ShareNetworkDataSource,
    @Secured private val provideShareService: ShareService,
    @Unsecured private val provideUnsecuredShareService: ShareService
) : ShareRepository {
    override suspend fun getPhotos(): Result<List<Photo>> {
        return runCatching { shareNetworkDataSource.getPhotos().toPhotos() }
    }

    override suspend fun getPhotoInfo(shareId: String): Result<SharePhoto> {
        return runCatching { provideShareService.getPhotoInfo(shareId).toSharePhoto() }
    }

    override suspend fun getUnsecuredPhotoInfo(shareId: String): Result<SharePhoto> {
        return runCatching { provideUnsecuredShareService.getPhotoInfo(shareId).toSharePhoto() }
    }

    override suspend fun acceptShare(photoId: Long): Result<AcceptShare> {
        return runCatching { provideShareService.acceptShare(photoId).toAcceptShare() }
    }
}
