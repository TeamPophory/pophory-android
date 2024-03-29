package com.teampophory.pophory.data.auth.repository

import com.teampophory.pophory.auth.entity.Token
import com.teampophory.pophory.auth.entity.UserAuthentication
import com.teampophory.pophory.auth.repository.AuthRepository
import com.teampophory.pophory.data.auth.model.SocialType
import com.teampophory.pophory.data.auth.service.AuthService
import com.teampophory.pophory.network.datastore.PophoryDataStore
import io.sentry.Sentry
import javax.inject.Inject

class DefaultAuthRepository @Inject constructor(
    private val dataStore: PophoryDataStore,
    private val service: AuthService,
) : AuthRepository {
    override fun isAutoLoginEnabled(): Boolean {
        return dataStore.autoLoginConfigured
    }

    override suspend fun login(socialToken: String): UserAuthentication {
        val authorization = "Bearer $socialToken"
        return service.login(authorization, SocialType("KAKAO")).toUserAuthentication()
    }

    override fun save(token: Token) {
        val (accessToken, refreshToken) = token
        dataStore.accessToken = accessToken
        dataStore.refreshToken = refreshToken
    }

    override fun configureAutoLogin(value: Boolean) {
        dataStore.autoLoginConfigured = value
    }

    override suspend fun withdraw() {
        service.withdraw("Bearer ${dataStore.accessToken}")
        dataStore.clear()
        expireSentry()
    }

    override suspend fun logout() {
        dataStore.clear()
        expireSentry()
    }

    private fun expireSentry() {
        Sentry.configureScope { scope -> scope.user = null }
    }
}
