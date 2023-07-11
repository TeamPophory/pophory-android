package com.teampophory.pophory.data.repository.photo

import com.teampophory.pophory.common.image.ContentUriRequestBody
import com.teampophory.pophory.common.okhttp.toPlainRequestBody
import com.teampophory.pophory.data.model.photo.Studio
import com.teampophory.pophory.data.network.model.album.PhotoListResponse
import com.teampophory.pophory.data.network.service.PhotoService
import okhttp3.RequestBody
import retrofit2.HttpException
import javax.inject.Inject

class DefaultPhotoRepository @Inject constructor(
    private val photoService: PhotoService
) : PhotoRepository {
    override suspend fun getPhotos(id: Int): Result<PhotoListResponse> {
        return runCatching { photoService.getPhotos(id) }
    }

    override suspend fun deletePhoto(photoId: Long): Result<Unit> {
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

    override suspend fun addPhoto(
        albumId: Int,
        takenAt: String,
        studioId: Long,
        photo: ContentUriRequestBody
    ): Result<Unit> {
        val file = photo.toFormData("photo")
        val request = HashMap<String, RequestBody>().apply {
            put("albumId", albumId.toString().toPlainRequestBody())
            put("takenAt", takenAt.toPlainRequestBody())
            put("studioId", studioId.toString().toPlainRequestBody())
        }
        val response = photoService.addPhoto(file, request)
        return runCatching {
            if (response.isSuccessful) {
                response.body()
            } else {
                throw HttpException(response)
            }
        }
    }
}