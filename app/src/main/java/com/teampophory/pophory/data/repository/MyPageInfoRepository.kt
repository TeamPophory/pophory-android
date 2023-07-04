package com.teampophory.pophory.data.repository

import com.teampophory.pophory.network.model.MyPageInfoResponse

interface MyPageInfoRepository {
    suspend fun getMyPageEmtpyInfo(): Result<MyPageInfoResponse>
    suspend fun getMyPageInfo(): Result<MyPageInfoResponse>
}