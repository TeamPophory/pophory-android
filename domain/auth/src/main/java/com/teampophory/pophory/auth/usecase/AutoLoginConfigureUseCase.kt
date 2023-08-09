package com.teampophory.pophory.auth.usecase

import com.teampophory.pophory.auth.repository.AuthRepository
import javax.inject.Inject

class AutoLoginConfigureUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(value: Boolean) = repository.configureAutoLogin(value)
}
