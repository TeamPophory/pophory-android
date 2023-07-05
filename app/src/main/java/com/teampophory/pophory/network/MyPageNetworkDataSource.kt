package com.teampophory.pophory.network

import com.teampophory.pophory.network.model.MyPageResponse

interface MyPageNetworkDataSource {
    suspend fun getMyPages(): MyPageResponse
}