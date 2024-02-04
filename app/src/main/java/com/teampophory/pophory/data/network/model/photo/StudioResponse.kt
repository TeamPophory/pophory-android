package com.teampophory.pophory.data.network.model.photo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias StudioEntity = com.teampophory.pophory.data.model.photo.Studio

@Serializable
data class StudioResponse(
    @SerialName("studios")
    val studios: List<Studio>,
) {
    @Serializable
    data class Studio(
        @SerialName("id")
        val id: Long,
        @SerialName("name")
        val name: String,
        @SerialName("imageUrl")
        val imageUrl: String? = null,
    ) {
        fun toStudio() = StudioEntity(
            id = id,
            name = name,
            imageUrl = imageUrl,
        )
    }

    fun toStudios() = studios.map { it.toStudio() }
}
