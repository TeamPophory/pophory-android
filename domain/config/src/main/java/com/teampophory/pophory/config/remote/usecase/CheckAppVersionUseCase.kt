package com.teampophory.pophory.config.remote.usecase

import com.teampophory.pophory.config.remote.repository.RemoteConfigRepository
import javax.inject.Inject

class CheckAppVersionUseCase @Inject constructor(
    private val remoteConfigRepository: RemoteConfigRepository
) {
    suspend operator fun invoke(currentVersion: String): Boolean {
        val latestVersion = remoteConfigRepository.getMinRequiredVersion()
        return currentVersion < latestVersion
    }
}
