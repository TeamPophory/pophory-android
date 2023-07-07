package com.teampophory.pophory.data.repository.fake

import com.teampophory.pophory.common.image.ContentUriRequestBody
import com.teampophory.pophory.data.model.photo.Studio
import com.teampophory.pophory.data.repository.photo.PhotoRepository
import com.teampophory.pophory.network.model.PhotoListResponse
import kotlinx.coroutines.delay

class FakePhotoRepository : PhotoRepository {
    private val fakeImageUrl =
        "https://images.unsplash.com/photo-1687023956422-117d5c47029d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=3432&q=80"

    override suspend fun getPhotos(): Result<PhotoListResponse> {
        delay(300)
        return runCatching {
            PhotoListResponse(
                listOf(
                    PhotoListResponse.Photo(
                        id = 1,
                        studio = "studio1",
                        takenAt = "2021-01-01",
                        imageUrl = fakeImageUrl
                    ),
                    PhotoListResponse.Photo(
                        id = 2,
                        studio = "studio2",
                        takenAt = "2021-01-02",
                        imageUrl = fakeImageUrl
                    ),
                    PhotoListResponse.Photo(
                        id = 3,
                        studio = "studio3",
                        takenAt = "2021-01-03",
                        imageUrl = fakeImageUrl
                    ),
                    PhotoListResponse.Photo(
                        id = 4,
                        studio = "studio4",
                        takenAt = "2021-01-04",
                        imageUrl = fakeImageUrl
                    )
                )
            )
        }
    }

    override suspend fun getStudios(): Result<List<Studio>> {
        TODO("Not yet implemented")
    }

    override suspend fun addPhoto(
        albumId: Int,
        takenAt: String,
        studioId: Int,
        photo: ContentUriRequestBody
    ): Result<Unit> {
        TODO("Not yet implemented")
    }
}