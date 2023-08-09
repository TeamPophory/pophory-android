package com.teampophory.pophory.data.network.authenticator

import android.content.Context
import android.content.Intent
import com.jakewharton.processphoenix.ProcessPhoenix
import com.kakao.sdk.user.UserApiClient
import com.teampophory.pophory.auth.repository.PophoryDataStore
import com.teampophory.pophory.data.auth.service.RefreshApi
import com.teampophory.pophory.feature.onboarding.OnBoardingActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PophoryAuthenticator @Inject constructor(
    private val dataStore: PophoryDataStore,
    private val api: RefreshApi,
    @ApplicationContext private val context: Context
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.header("Authorization") == null) {
            return null
        }

        if (response.code == 401) {
            val refreshToken = dataStore.refreshToken
            val newTokens = runCatching {
                runBlocking {
                    api.refreshToken("Bearer $refreshToken")
                }
            }.onSuccess {
                dataStore.refreshToken = it.refreshToken
                dataStore.accessToken = it.accessToken
            }.onFailure {
                Timber.e(it)
                runBlocking {
                    UserApiClient.instance.logout { error ->
                        Timber.e(error)
                        dataStore.clear()
                        ProcessPhoenix.triggerRebirth(
                            context,
                            Intent(context, OnBoardingActivity::class.java)
                        )
                    }
                }
            }.getOrThrow()

            return response.request.newBuilder()
                .header("Authorization", "Bearer ${newTokens.accessToken}")
                .build()
        }
        return null
    }
}
