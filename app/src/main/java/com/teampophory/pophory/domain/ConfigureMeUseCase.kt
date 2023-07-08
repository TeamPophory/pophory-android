package com.teampophory.pophory.domain

import com.teampophory.pophory.data.local.PophoryDataStore
import com.teampophory.pophory.data.repository.my.MyPageRepository
import io.sentry.Sentry
import io.sentry.protocol.User
import timber.log.Timber
import javax.inject.Inject

class ConfigureMeUseCase @Inject constructor(
    private val repository: MyPageRepository,
    private val dataStore: PophoryDataStore
) {
    suspend operator fun invoke() {
        repository.getMyPageInfo()
            .onSuccess { me ->
                dataStore.userId = me.nickname
                dataStore.userName = me.realName
                val user = User().apply {
                    username = me.nickname
                    name = me.realName
                }
                Sentry.setUser(user)
            }.onFailure(Timber::e)
    }
}
