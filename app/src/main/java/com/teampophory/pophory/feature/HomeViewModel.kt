package com.teampophory.pophory.feature

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.domain.ConfigureMeUseCase
import com.teampophory.pophory.feature.home.store.model.AlbumItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val configureMeUseCase: ConfigureMeUseCase
) : ViewModel() {
    private val _currentAlbum = MutableStateFlow<List<AlbumItem>?>(null)
    val currentAlbum: StateFlow<List<AlbumItem>?> get() = _currentAlbum

    private val _currentAlbumPosition = MutableStateFlow(0)
    val currentAlbumPosition: StateFlow<Int> get() = _currentAlbumPosition

    private val _albumCountUpdate = MutableLiveData<Unit>()
    val albumCountUpdate: MutableLiveData<Unit> get() = _albumCountUpdate

    init {
        viewModelScope.launch {
            configureMeUseCase()
        }
    }

    fun onUpdateAlbum(album: List<AlbumItem>?) {
        viewModelScope.launch {
            _currentAlbum.emit(album)
        }
    }

    fun onUpdateAlbumPosition(position: Int) {
        viewModelScope.launch {
            _currentAlbumPosition.emit(position)
        }
    }

    fun eventAlbumCountUpdate() {
        _albumCountUpdate.value = Unit
    }
}
