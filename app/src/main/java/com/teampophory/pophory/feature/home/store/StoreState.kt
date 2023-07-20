package com.teampophory.pophory.feature.home.store

import com.teampophory.pophory.feature.home.store.model.AlbumItem

sealed class StoreState {
    data object Uninitialized : StoreState()
    data object Loading : StoreState()
    data class SuccessAlbums(val data: List<AlbumItem>) : StoreState()
    data class Error(val error: Throwable) : StoreState()
}
