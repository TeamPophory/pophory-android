package com.teampophory.pophory.config.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class MinimumVersionResponse(
    val versions: List<Version>
)

@Serializable
data class Version(
    val os: String,
    val version: String
)
