package com.teampophory.pophory.data.repository

import com.teampophory.pophory.network.model.MyPageResponse

interface MyPageInfoRepository {
    suspend fun getMyPageInfo(): Result<MyPageResponse>
}