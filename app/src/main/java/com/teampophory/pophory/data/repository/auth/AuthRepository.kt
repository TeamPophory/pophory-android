package com.teampophory.pophory.data.repository.auth

import com.teampophory.pophory.data.model.auth.Token
import com.teampophory.pophory.data.model.auth.UserAuthentication

interface AuthRepository {
    suspend fun login(socialToken: String): UserAuthentication
    fun save(token: Token)
    fun configureAutoLogin(value: Boolean)
    suspend fun withdraw()
    suspend fun logout()
}
