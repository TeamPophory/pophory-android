package com.teampophory.pophory.feature

import androidx.lifecycle.ViewModel
import com.teampophory.pophory.feature.home.store.model.AlbumItem

class HomeViewModel : ViewModel() {
    var currentAlbum: AlbumItem? = null
        private set

    fun onUpdateAlbum(album: AlbumItem?) {
        currentAlbum = album
    }
}
