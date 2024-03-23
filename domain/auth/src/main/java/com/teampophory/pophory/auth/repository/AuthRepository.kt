package com.teampophory.pophory.auth.repository

import com.teampophory.pophory.auth.entity.Token
import com.teampophory.pophory.auth.entity.UserAuthentication

interface AuthRepository {
    fun isAutoLoginEnabled(): Boolean
    suspend fun login(socialToken: String): UserAuthentication
    suspend fun signUp(realName: String, nickName: String, albumCover: Int): Int
    suspend fun validateNickname(nickname: String): Boolean
    fun save(token: Token)
    fun configureAutoLogin(value: Boolean)
    suspend fun withdraw()
    suspend fun logout()
}
