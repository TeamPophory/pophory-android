package com.teampophory.pophory.data.config.repository

import com.teampophory.pophory.config.remote.datasource.RemoteConfigDataSource
import com.teampophory.pophory.config.remote.repository.RemoteConfigRepository
import javax.inject.Inject

class DefaultRemoteConfigRepository @Inject constructor(
    private val remoteConfigDataSource: RemoteConfigDataSource
) : RemoteConfigRepository {

    override suspend fun getMinRequiredVersion(): String {
        val minVersion = remoteConfigDataSource.getString(KEY_MIN_VERSION, "1.4.0")
        return minVersion
    }

    companion object {
        const val KEY_MIN_VERSION = "minimum_update_version_android"
    }
}
