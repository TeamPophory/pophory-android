package com.teampophory.pophory.data.repository.auth

import com.teampophory.pophory.data.local.PophoryDataStore
import com.teampophory.pophory.data.model.auth.Token
import com.teampophory.pophory.data.model.auth.UserAuthentication
import com.teampophory.pophory.data.network.model.auth.SocialType
import com.teampophory.pophory.data.network.service.AuthService
import io.sentry.Sentry
import javax.inject.Inject


class DefaultAuthRepository @Inject constructor(
    private val dataStore: PophoryDataStore,
    private val service: AuthService
) : AuthRepository {
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
