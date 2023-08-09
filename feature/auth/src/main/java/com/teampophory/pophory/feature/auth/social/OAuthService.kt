package com.teampophory.pophory.feature.auth.social

import com.teampophory.pophory.auth.entity.OAuthToken

interface OAuthService {
    suspend fun login(): OAuthToken
    suspend fun logout()
    suspend fun withdraw()
}
