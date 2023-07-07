package com.teampophory.pophory.data.repository.my

import com.teampophory.pophory.network.MyPageNetworkDataSource
import com.teampophory.pophory.network.model.MyPageResponse
import javax.inject.Inject

class DefaultMyPageRepository @Inject constructor(
    private val retrofitMyPageNetwork: MyPageNetworkDataSource
) : MyPageRepository {
    override suspend fun getMyPageInfo(): Result<MyPageResponse> {
        return runCatching { retrofitMyPageNetwork.getMyPages() }
    }
}