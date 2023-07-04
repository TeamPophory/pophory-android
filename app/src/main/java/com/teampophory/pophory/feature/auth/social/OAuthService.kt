package com.teampophory.pophory.feature.auth.social

import com.teampophory.pophory.feature.auth.model.OAuthToken

interface OAuthService {
    suspend fun login(): OAuthToken
    suspend fun logout()
    suspend fun withdraw()
}
