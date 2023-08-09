package com.teampophory.pophory.data.auth.repository

import com.teampophory.pophory.auth.repository.PophoryDataStore
import com.teampophory.pophory.auth.repository.AuthRepository
import com.teampophory.pophory.data.network.model.auth.SocialType
import com.teampophory.pophory.data.network.service.AuthService
import io.sentry.Sentry
import javax.inject.Inject


class DefaultAuthRepository @Inject constructor(
    private val dataStore: PophoryDataStore,
    private val service: AuthService
) : AuthRepository {
    override suspend fun login(socialToken: String): com.teampophory.pophory.auth.entity.UserAuthentication {
        val authorization = "Bearer $socialToken"
        return service.login(authorization, SocialType("KAKAO")).toUserAuthentication()
    }

    override fun save(token: com.teampophory.pophory.auth.entity.Token) {
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
