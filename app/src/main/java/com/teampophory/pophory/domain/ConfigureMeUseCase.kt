package com.teampophory.pophory.domain

import com.teampophory.pophory.auth.repository.PophoryDataStore
import com.teampophory.pophory.data.repository.my.MyPageRepository
import com.teampophory.pophory.data.network.model.mypage.MyPageResponse
import javax.inject.Inject

class ConfigureMeUseCase @Inject constructor(
    private val repository: MyPageRepository,
    private val dataStore: PophoryDataStore
) {
    suspend operator fun invoke(): MyPageResponse? {
        return repository.getMyPageInfo()
            .onSuccess { me ->
                dataStore.userId = me.nickname
                dataStore.userName = me.realName
            }.getOrNull()
    }
}
