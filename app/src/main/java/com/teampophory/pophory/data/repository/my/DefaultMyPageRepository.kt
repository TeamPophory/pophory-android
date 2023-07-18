package com.teampophory.pophory.data.repository.my

import com.teampophory.pophory.data.network.model.mypage.MyPageResponse
import com.teampophory.pophory.data.network.service.MypageService
import javax.inject.Inject

class DefaultMyPageRepository @Inject constructor(
    private val retrofitMyPageNetwork: MypageService
) : MyPageRepository {
    override suspend fun getMyPageInfo(): Result<MyPageResponse> {
        return runCatching { retrofitMyPageNetwork.getMyPageInfo() }
    }
}