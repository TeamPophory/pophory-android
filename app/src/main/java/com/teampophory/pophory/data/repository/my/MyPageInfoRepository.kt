package com.teampophory.pophory.data.repository.my

import com.teampophory.pophory.data.network.model.mypage.MyPageResponse

interface MyPageRepository {
    suspend fun getMyPageInfo(): Result<MyPageResponse>
}
