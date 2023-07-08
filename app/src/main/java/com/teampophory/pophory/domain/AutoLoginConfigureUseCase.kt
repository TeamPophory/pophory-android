package com.teampophory.pophory.domain

import com.teampophory.pophory.data.repository.auth.AuthRepository
import javax.inject.Inject

class AutoLoginConfigureUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(value: Boolean) = repository.configureAutoLogin(value)
}
