package com.teampophory.pophory.data.repository.my

import com.teampophory.pophory.network.model.MyPageResponse

interface MyPageRepository {
    suspend fun getMyPageInfo(): Result<MyPageResponse>
}