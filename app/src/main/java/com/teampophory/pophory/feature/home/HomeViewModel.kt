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
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val configureMeUseCase: ConfigureMeUseCase
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeViewState())
    val homeState: StateFlow<HomeViewState> = _homeState.asStateFlow()

    private val _albumCountUpdateEvent: MutableSharedFlow<Unit> = MutableSharedFlow()

    val albumCountUpdateEvent: SharedFlow<Unit> = _albumCountUpdateEvent.asSharedFlow()


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
        _homeState.value = _homeState.value.copy(currentAlbums = album)
    }

    fun onUpdateAlbumPosition(position: Int) {
        _homeState.value = _homeState.value.copy(currentAlbumPosition = position)
    }

    fun eventAlbumCountUpdate() {
        viewModelScope.launch {
            _albumCountUpdateEvent.emit(Unit)
        }
    }
}