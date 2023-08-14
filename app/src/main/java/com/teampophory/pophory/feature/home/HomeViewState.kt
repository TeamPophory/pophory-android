package com.teampophory.pophory.feature.home

import com.teampophory.pophory.feature.home.store.model.AlbumItem

data class HomeViewState(
    val currentAlbums: List<AlbumItem>? = null,
    val currentAlbumPosition: Int = 0,
)