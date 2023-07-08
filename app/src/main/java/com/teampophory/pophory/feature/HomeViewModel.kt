package com.teampophory.pophory.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.domain.ConfigureMeUseCase
import com.teampophory.pophory.feature.home.store.model.AlbumItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchMeUseCase: ConfigureMeUseCase
) : ViewModel() {
    private val _currentAlbum = MutableStateFlow<AlbumItem?>(null)
    val currentAlbum: StateFlow<AlbumItem?> get() = _currentAlbum

    init {
        viewModelScope.launch {
            fetchMeUseCase()
                .onSuccess {

                }.onFailure(Timber::e)
        }
    }

    fun onUpdateAlbum(album: AlbumItem?) {
        viewModelScope.launch {
            _currentAlbum.emit(album)
        }
    }
}
