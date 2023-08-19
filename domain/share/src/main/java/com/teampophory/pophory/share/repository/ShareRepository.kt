package com.teampophory.pophory.share.repository

import com.teampophory.pophory.share.entity.AcceptedSharePhoto
import com.teampophory.pophory.share.entity.Photo
import com.teampophory.pophory.share.entity.SharePhoto

interface ShareRepository {
    suspend fun getPhotos(): Result<List<Photo>>
    suspend fun getPhotoInfo(shareId: String): Result<SharePhoto>
    suspend fun getUnsecuredPhotoInfo(shareId: String): Result<SharePhoto>
    suspend fun acceptShare( photoId: Long): Result<AcceptedSharePhoto>
}
