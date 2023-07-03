package com.teampophory.pophory.data.repository.fake

import com.teampophory.pophory.data.repository.PhotoRepository
import com.teampophory.pophory.network.model.MyPageInfoResponse
import com.teampophory.pophory.network.model.PhotoListResponse
import kotlinx.coroutines.delay

class FakeMyPageInfoRepository : PhotoRepository {
    private val fakeImageUrl =
        "https://images.unsplash.com/photo-1687023956422-117d5c47029d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=3432&q=80"

    override suspend fun getMyPageInfo(): Result<MyPageInfoResponse> {
        delay(300)
        return runCatching {
            MyPageInfoResponse(
                "한수아",
                "HANSUAH",
                "",
                5,
                listOf(
                    MyPageInfoResponse.Photo(
                        1,
                        fakeImageUrl,
                        "인생네컷",
                        "2023.06.26"
                    ),
                    MyPageInfoResponse.Photo(
                        2,
                        fakeImageUrl,
                        "인생네컷",
                        "2023.06.26"
                    ),
                    MyPageInfoResponse.Photo(
                        3,
                        fakeImageUrl,
                        "인생네컷",
                        "2023.06.26"
                    ),
                    MyPageInfoResponse.Photo(
                        4,
                        fakeImageUrl,
                        "인생네컷",
                        "2023.06.26"
                    ),
                    MyPageInfoResponse.Photo(
                        5,
                        fakeImageUrl,
                        "인생네컷",
                        "2023.06.26"
                    )
                )
            )
        }
    }

    override suspend fun getPhotos(): Result<PhotoListResponse> {
        TODO("Not yet implemented")
    }
}