package com.teampophory.pophory.auth.usecase

import com.teampophory.pophory.auth.repository.AuthRepository
import javax.inject.Inject

class GetAutoLoginConfigurationUseCase @Inject constructor(
    private val dataStore: AuthRepository,
) {
    operator fun invoke(): Boolean {
        return dataStore.isAutoLoginEnabled()
    }
}
