package com.teampophory.pophory.network.model

import com.teampophory.pophory.feature.home.store.model.AlbumItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoreResponse(
    @SerialName("albums")
    val albums: List<Album>
) {
    @Serializable
    data class Album(
        @SerialName("id")
        val id: Int,
        @SerialName("title")
        val title: String,
        @SerialName("photoCount")
        val photoCount: Int
    )

    fun toAlbums(): List<AlbumItem> {
        return albums.map { album ->
            AlbumItem(
                id = album.id,
                title = album.title,
                photoCount = album.photoCount
            )
        }
    }

}