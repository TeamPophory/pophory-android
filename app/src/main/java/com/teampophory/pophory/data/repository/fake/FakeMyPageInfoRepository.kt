package com.teampophory.pophory.data.repository.fake

import com.teampophory.pophory.data.repository.MyPageInfoRepository
import com.teampophory.pophory.network.model.MyPageResponse
import kotlinx.coroutines.delay

class FakeMyPageInfoRepository : MyPageInfoRepository {
    private val fakeImageUrl =
        "https://images.unsplash.com/photo-1687023956422-117d5c47029d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=3432&q=80"

    override suspend fun getMyPageInfo(): Result<MyPageResponse> {
        delay(300)
        return runCatching {
            MyPageResponse(
                "한수아",
                "HANSUAH",
                "",
                5,
                listOf(

                )
            )
        }
    }
}