package com.teampophory.pophory.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.feature.home.store.model.AlbumItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _currentAlbum = MutableStateFlow<AlbumItem?>(null)
    val currentAlbum: StateFlow<AlbumItem?> get() = _currentAlbum

    fun onUpdateAlbum(album: AlbumItem?) {
        viewModelScope.launch {
            _currentAlbum.emit(album)
        }
    }
}
