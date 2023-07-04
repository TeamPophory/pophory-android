package com.teampophory.pophory.data.repository.auth

import com.teampophory.pophory.data.network.model.auth.Token
import com.teampophory.pophory.data.network.model.auth.UserAuthentication

interface AuthRepository {
    suspend fun login(socialToken: String): UserAuthentication
    fun save(token: Token)
    suspend fun withdraw()
    suspend fun logout()
}
