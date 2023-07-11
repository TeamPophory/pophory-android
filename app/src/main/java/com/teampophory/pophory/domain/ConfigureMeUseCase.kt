package com.teampophory.pophory.domain

import com.teampophory.pophory.data.local.PophoryDataStore
import com.teampophory.pophory.data.repository.my.MyPageRepository
import com.teampophory.pophory.network.model.MyPageResponse
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
