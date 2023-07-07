package com.teampophory.pophory.domain

import com.teampophory.pophory.data.model.auth.UserAccountState
import com.teampophory.pophory.data.repository.auth.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(token: String) = runCatching {
        val authentication = repository.login(token)
        repository.save(authentication.token)
        UserAccountState.of(authentication.isRegistered)
    }
}
