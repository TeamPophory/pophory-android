package com.teampophory.pophory.auth.usecase

import com.teampophory.pophory.auth.entity.UserAccountState
import com.teampophory.pophory.auth.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(token: String) = runCatching {
        val authentication = repository.login(token)
        repository.save(authentication.token)
        UserAccountState.of(authentication.isRegistered)
    }
}
