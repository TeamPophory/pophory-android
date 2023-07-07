package com.teampophory.pophory.data.repository.photo

import com.teampophory.pophory.common.image.ContentUriRequestBody
import com.teampophory.pophory.common.okhttp.toPlainRequestBody
import com.teampophory.pophory.data.model.photo.Studio
import com.teampophory.pophory.data.network.model.album.PhotoListResponse
import com.teampophory.pophory.data.network.service.AlbumService
import okhttp3.RequestBody
import javax.inject.Inject

class DefaultPhotoRepository @Inject constructor(
    private val albumService: AlbumService
) : PhotoRepository {
    override suspend fun getPhotos(id: Int): Result<PhotoListResponse> {
        return runCatching { albumService.getPhotos(id) }
    }

    override suspend fun deletePhoto(photoId: Long): Result<Unit> {
        return runCatching { albumService.deletePhoto(photoId) }
    }

    override suspend fun getStudios(): Result<List<Studio>> {
        return runCatching { albumService.fetchStudios().toStudios() }
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
        return runCatching {
            albumService.addPhoto(file, request)
        }
    }
}
