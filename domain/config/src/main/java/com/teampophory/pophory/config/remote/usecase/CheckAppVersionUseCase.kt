package com.teampophory.pophory.domain.config.usecase

import com.teampophory.pophory.config.remote.repository.RemoteConfigRepository
import javax.inject.Inject

class CheckAppVersionUseCase @Inject constructor(
    private val remoteConfigRepository: RemoteConfigRepository
) {

    suspend operator fun invoke(currentVersion: String): Boolean {
        val minRequiredVersion = remoteConfigRepository.getMinRequiredVersion()
        return isUpdateRequired(currentVersion, minRequiredVersion)
    }

    private fun isUpdateRequired(currentVersion: String, minRequiredVersion: String): Boolean {
        val currentVersionParts = currentVersion.split(".").map { it.toIntOrNull() ?: 0 }
        val minVersionParts = minRequiredVersion.split(".").map { it.toIntOrNull() ?: 0 }

        for (i in currentVersionParts.indices) {
            val currentPart = currentVersionParts.getOrElse(i) { 0 }
            val minPart = minVersionParts.getOrElse(i) { 0 }
            if (currentPart < minPart) {
                return true
            } else if (currentPart > minPart) {
                return false
            }
        }
        return false
    }
}
