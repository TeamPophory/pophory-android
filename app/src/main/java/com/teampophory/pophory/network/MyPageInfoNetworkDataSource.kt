package com.teampophory.pophory.network

import com.teampophory.pophory.network.model.MyPageResponse

interface MyPageInfoNetworkDataSource {
    suspend fun getMyPageInfo(): MyPageResponse
}