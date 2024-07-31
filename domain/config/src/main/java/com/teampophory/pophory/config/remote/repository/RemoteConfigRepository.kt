package com.teampophory.pophory.config.remote.repository

interface RemoteConfigRepository {
    suspend fun getMinRequiredVersion(): String
}
