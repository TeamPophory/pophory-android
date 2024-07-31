package com.teampophory.pophory.config.remote.repository

import com.teampophory.pophory.config.remote.model.MinimumVersionService
import javax.inject.Inject

class DefaultRemoteConfigRepository @Inject constructor(
    private val service: MinimumVersionService
) : RemoteConfigRepository {

    override suspend fun getMinRequiredVersion(): String {
        val response = service.getVersions()
        return response.versions.find { it.os == "AOS" }?.version
            ?: throw IllegalStateException("AOS version not found")
    }
}
