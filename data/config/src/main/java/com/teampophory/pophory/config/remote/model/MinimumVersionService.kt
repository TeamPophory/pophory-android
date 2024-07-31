package com.teampophory.pophory.config.remote.model

import retrofit2.http.GET

interface MinimumVersionService {
    @GET("version")
    suspend fun getVersions(): MinimumVersionResponse
}