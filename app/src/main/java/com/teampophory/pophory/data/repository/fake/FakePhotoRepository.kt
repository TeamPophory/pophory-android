package com.teampophory.pophory.data.repository.fake

import com.teampophory.pophory.data.model.photo.Studio
import com.teampophory.pophory.data.network.model.album.AlbumCoverChangeRequest
import com.teampophory.pophory.data.network.model.album.PhotoListResponse
import com.teampophory.pophory.domain.model.S3Image
import com.teampophory.pophory.domain.repository.photo.PhotoRepository
import kotlinx.coroutines.delay
import okhttp3.RequestBody

class FakePhotoRepository : PhotoRepository {
    private val fakeImageUrl =
        "https://images.unsplash.com/photo-1687023956422-117d5c47029d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=3432&q=80"

    override suspend fun getPhotos(id: Long): Result<PhotoListResponse> {
        delay(300)
        return runCatching {
            PhotoListResponse(
                listOf(
                    PhotoListResponse.Photo(
                        id = 1,
                        studio = "studio1",
                        takenAt = "2021-01-01",
                        imageUrl = fakeImageUrl,
                        shareId = "shareId",
                    ),
                    PhotoListResponse.Photo(
                        id = 2,
                        studio = "studio2",
                        takenAt = "2021-01-02",
                        imageUrl = fakeImageUrl,
                        shareId = "shareId",
                    ),
                    PhotoListResponse.Photo(
                        id = 3,
                        studio = "studio3",
                        takenAt = "2021-01-03",
                        imageUrl = fakeImageUrl,
                        shareId = "shareId",
                    ),
                    PhotoListResponse.Photo(
                        id = 4,
                        studio = "studio4",
                        takenAt = "2021-01-04",
                        imageUrl = fakeImageUrl,
                        shareId = "shareId",
                    ),
                ),
            )
        }
    }

    override suspend fun getStudios(): Result<List<Studio>> {
        delay(300)
        return runCatching {
            listOf(
                Studio(
                    id = 1,
                    name = "studio1",
                    imageUrl = fakeImageUrl,
                ),
                Studio(
                    id = 2,
                    name = "studio2",
                    imageUrl = fakeImageUrl,
                ),
                Studio(
                    id = 3,
                    name = "studio3",
                    imageUrl = fakeImageUrl,
                ),
                Studio(
                    id = 4,
                    name = "studio4",
                    imageUrl = fakeImageUrl,
                ),
            )
        }
    }

    override suspend fun addPhotoToPophory(
        albumId: Long,
        takenAt: String,
        studioId: Long,
        fileName: String,
        width: Int,
        height: Int,
    ): Result<Unit> {
        return runCatching { }
    }

    override suspend fun getPhotoInfoFromS3(): Result<S3Image> {
        return runCatching { S3Image("fileName", fakeImageUrl) }
    }

    override suspend fun postPhotoToS3(url: String, photo: RequestBody) = Unit
    override suspend fun patchAlbumCover(
        albumCoverId: Long,
        albumCoverChangeRequest: AlbumCoverChangeRequest,
    ): Result<Unit> {
        return runCatching { }
    }

    override suspend fun deletePhoto(photoId: Long): Result<Unit> {
        delay(300)
        return runCatching { }
    }
}
