package com.teampophory.pophory.data.repository.photo

import com.teampophory.pophory.common.image.ContentUriRequestBody
import com.teampophory.pophory.config.di.qualifier.Unsecured
import com.teampophory.pophory.data.model.photo.Studio
import com.teampophory.pophory.data.network.model.album.PhotoListResponse
import com.teampophory.pophory.data.network.model.photo.PhotoRequest
import com.teampophory.pophory.data.network.service.PhotoService
import com.teampophory.pophory.domain.model.PhotoInfoFromS3
import com.teampophory.pophory.domain.repository.photo.PhotoRepository
import retrofit2.HttpException
import javax.inject.Inject

class DefaultPhotoRepository @Inject constructor(
    private val photoService: PhotoService,
    @Unsecured private val photoServiceNonToken: PhotoService
) : PhotoRepository {
    override suspend fun getPhotos(id: Int): Result<PhotoListResponse> {
        return runCatching { photoService.getPhotos(id) }
    }

    override suspend fun deletePhoto(photoId: Int): Result<Unit> {
        return runCatching {
            val response = photoService.deletePhoto(photoId)
            if (response.isSuccessful) {
                response.body()
            } else {
                throw HttpException(response)
            }
        }
    }

    override suspend fun getStudios(): Result<List<Studio>> {
        return runCatching { photoService.fetchStudios().toStudios() }
    }

    override suspend fun addPhotoToPophory(
        albumId: Int,
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
        val response = photoService.addPhotoToPophory(request)
        return runCatching {
            if (response.isSuccessful) {
                response.body()
            } else {
                throw HttpException(response)
            }
        }
    }

    override suspend fun addPhotoToS3(url: String, photo: ContentUriRequestBody): Result<Unit> {
        val file = photo.toFormData("photo")
        val response = photoServiceNonToken.addPhotoToS3(url, file)
        return runCatching {
            if (response.isSuccessful) {
                response.body()
            } else {
                throw HttpException(response)
            }
        }
    }

    override suspend fun getPhotoInfoFromS3(): Result<PhotoInfoFromS3> {
        return runCatching { photoService.getPhotoInfoFromS3().toPhotoInfoFromS3() }
    }
}