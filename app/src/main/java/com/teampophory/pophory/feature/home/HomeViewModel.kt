package com.teampophory.pophory.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.domain.ConfigureMeUseCase
import com.teampophory.pophory.feature.home.store.model.AlbumItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sentry.Sentry
import io.sentry.protocol.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val configureMeUseCase: ConfigureMeUseCase
) : ViewModel() {
    private val _currentAlbums = MutableStateFlow<List<AlbumItem>?>(null)
    val currentAlbums: StateFlow<List<AlbumItem>?> get() = _currentAlbums

    private val _currentAlbumPosition = MutableStateFlow(0)
    val currentAlbumPosition: StateFlow<Int> get() = _currentAlbumPosition

    private val _albumCountUpdate = MutableSharedFlow<Unit>()
    val albumCountUpdate: SharedFlow<Unit> get() = _albumCountUpdate

    init {
        viewModelScope.launch {
            configureMeUseCase()?.let {
                val user = User().apply {
                    username = it.nickname
                    name = it.realName
                }
                Sentry.setUser(user)
            }
        }
    }

    fun onUpdateAlbum(album: List<AlbumItem>?) {
        viewModelScope.launch {
            _currentAlbums.emit(album)
        }
    }

    fun onUpdateAlbumPosition(position: Int) {
        viewModelScope.launch {
            _currentAlbumPosition.emit(position)
        }
    }

    fun eventAlbumCountUpdate() {
        viewModelScope.launch {
            _albumCountUpdate.emit(Unit)
        }
    }
}
