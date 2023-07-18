package com.teampophory.pophory.data.network.service

import com.teampophory.pophory.data.network.model.mypage.MyPageResponse
import retrofit2.http.GET

interface MypageService {
    @GET("api/v2/member")
    suspend fun getMyPageInfo(): MyPageResponse
}