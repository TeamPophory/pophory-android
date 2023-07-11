package com.teampophory.pophory.network.retrofit.photo

import com.teampophory.pophory.common.image.ContentUriRequestBody
import com.teampophory.pophory.common.okhttp.toPlainRequestBody
import com.teampophory.pophory.data.network.service.PhotoService
import com.teampophory.pophory.network.PhotoNetworkDataSource
import okhttp3.RequestBody
import retrofit2.HttpException
import javax.inject.Inject

class PhotoNetwork @Inject constructor(
    private val photoService: PhotoService
) : PhotoNetworkDataSource {

    override suspend fun addPhoto(
        albumId: Int,
        takenAt: String,
        studioId: Long,
        photo: ContentUriRequestBody
    ): Unit? {
        val file = photo.toFormData("photo")
        val request = HashMap<String, RequestBody>().apply {
            put("albumId", albumId.toString().toPlainRequestBody())
            put("takenAt", takenAt.toPlainRequestBody())
            put("studioId", studioId.toString().toPlainRequestBody())
        }
        val response = photoService.addPhoto(file, request)
        return if (response.isSuccessful) {
            response.body()
        } else {
            throw HttpException(response)
        }
    }

    override suspend fun deletePhoto(photoId: Long): Unit? {
        val response = photoService.deletePhoto(photoId)
        return if (response.isSuccessful) {
            response.body()
        } else {
            throw HttpException(response)
        }
    }
}