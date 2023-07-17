package com.teampophory.pophory.network.retrofit.mypage

import com.teampophory.pophory.network.MyPageNetworkDataSource
import com.teampophory.pophory.network.model.MyPageResponse
import retrofit2.http.GET
import javax.inject.Inject

interface RetrofitMyPageNetworkApi {
    @GET("api/v2/member")
    suspend fun getMyPageInfo(): MyPageResponse
}

class RetrofitMyPageNetwork @Inject constructor(
    private val networkApi: RetrofitMyPageNetworkApi
) : MyPageNetworkDataSource {
    override suspend fun getMyPages(): MyPageResponse {
        return networkApi.getMyPageInfo()
    }
}