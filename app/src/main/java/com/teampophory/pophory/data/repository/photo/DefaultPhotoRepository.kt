package com.teampophory.pophory.data.repository.photo

import com.teampophory.pophory.common.okhttp.getResponseBodyOrThrow
import com.teampophory.pophory.config.di.qualifier.Unsecured
import com.teampophory.pophory.data.model.photo.Studio
import com.teampophory.pophory.data.network.model.album.AlbumCoverChangeRequest
import com.teampophory.pophory.data.network.model.album.PhotoListResponse
import com.teampophory.pophory.data.network.model.photo.PhotoRequest
import com.teampophory.pophory.data.network.service.PhotoService
import com.teampophory.pophory.domain.model.S3Image
import com.teampophory.pophory.domain.repository.photo.PhotoRepository
import okhttp3.RequestBody
import javax.inject.Inject

class DefaultPhotoRepository @Inject constructor(
    private val photoService: PhotoService,
    @Unsecured private val photoServiceNonToken: PhotoService
) : PhotoRepository {
    override suspend fun getPhotos(id: Long): Result<PhotoListResponse> {
        return runCatching { photoService.getPhotos(id) }
    }

    override suspend fun deletePhoto(photoId: Long): Result<Unit> {
        return runCatching {
            photoService.deletePhoto(photoId).getResponseBodyOrThrow()
        }
    }

    override suspend fun getStudios(): Result<List<Studio>> {
        return runCatching { photoService.fetchStudios().toStudios() }
    }

    override suspend fun addPhotoToPophory(
        albumId: Long,
        takenAt: String,
        studioId: Long,
        fileName: String,
        width: Int,
        height: Int
    ): Result<Unit> {
        val request = PhotoRequest(
            albumId = albumId,
            takenAt = takenAt,
            studioId = studioId,
            fileName = fileName,
            width = width,
            height = height
        )
        return runCatching {
            photoService.addPhotoToPophory(request).getResponseBodyOrThrow()
        }
    }

    override suspend fun postPhotoToS3(url: String, photo: RequestBody) {
        photoServiceNonToken.postPhotoToS3(url, photo).getResponseBodyOrThrow()
    }

    override suspend fun getPhotoInfoFromS3(): Result<S3Image> {
        return runCatching { photoService.getPhotoInfoFromS3().toS3Image() }
    }

    override suspend fun patchAlbumCover(
        albumCoverId: Long,
        albumCoverChangeRequest: AlbumCoverChangeRequest
    ): Result<Unit> {
        return runCatching {
            photoService.patchAlbumCover(albumCoverId, albumCoverChangeRequest)
                .getResponseBodyOrThrow()
        }
    }
}