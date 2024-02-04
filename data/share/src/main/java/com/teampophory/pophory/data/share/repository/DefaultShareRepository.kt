package com.teampophory.pophory.data.share.repository

import com.teampophory.pophory.common.qualifier.Secured
import com.teampophory.pophory.common.qualifier.Unsecured
import com.teampophory.pophory.data.share.model.toAcceptShare
import com.teampophory.pophory.data.share.model.toSharePhoto
import com.teampophory.pophory.data.share.service.ShareNetworkDataSource
import com.teampophory.pophory.data.share.service.ShareService
import com.teampophory.pophory.share.entity.AcceptedSharePhoto
import com.teampophory.pophory.share.entity.Photo
import com.teampophory.pophory.share.entity.SharePhoto
import com.teampophory.pophory.share.repository.ShareRepository
import javax.inject.Inject

class DefaultShareRepository @Inject constructor(
    private val shareNetworkDataSource: ShareNetworkDataSource,
    @Secured private val shareService: ShareService,
    @Unsecured private val unsecuredShareService: ShareService,
) : ShareRepository {
    override suspend fun getPhotos(): Result<List<Photo>> {
        return runCatching { shareNetworkDataSource.getPhotos().toPhotos() }
    }

    override suspend fun getPhotoInfo(shareId: String): Result<SharePhoto> {
        return runCatching { shareService.getPhotoInfo(shareId).toSharePhoto() }
    }

    override suspend fun getUnsecuredPhotoInfo(shareId: String): Result<SharePhoto> {
        return runCatching { unsecuredShareService.getPhotoInfo(shareId).toSharePhoto() }
    }

    override suspend fun acceptShare(photoId: Long): Result<AcceptedSharePhoto> {
        return runCatching { shareService.acceptShare(photoId).toAcceptShare() }
    }
}
