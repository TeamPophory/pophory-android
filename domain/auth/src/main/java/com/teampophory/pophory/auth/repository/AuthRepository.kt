package com.teampophory.pophory.auth.repository

import com.teampophory.pophory.auth.entity.Token
import com.teampophory.pophory.auth.entity.UserAuthentication

interface AuthRepository {
    suspend fun login(socialToken: String): UserAuthentication
    fun save(token: Token)
    fun configureAutoLogin(value: Boolean)
    suspend fun withdraw()
    suspend fun logout()
}
