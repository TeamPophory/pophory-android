package com.teampophory.pophory.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.BuildConfig
import com.teampophory.pophory.ad.usecase.GetAdConstantUseCase
import com.teampophory.pophory.ad.usecase.SetAdConstantUseCase
import com.teampophory.pophory.domain.usecase.ConfigureMeUseCase
import com.teampophory.pophory.feature.home.store.model.AlbumItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val configureMeUseCase: ConfigureMeUseCase,
    private val getAdConstantUseCase: GetAdConstantUseCase,
    private val setAdConstantUseCase: SetAdConstantUseCase,
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeViewState())
    val homeState: StateFlow<HomeViewState> = _homeState.asStateFlow()

    private val _albumCountUpdateEvent: MutableSharedFlow<Unit> = MutableSharedFlow()

    val albumCountUpdateEvent: SharedFlow<Unit> = _albumCountUpdateEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            configureMeUseCase()
            getAdConstantUseCase("android", BuildConfig.VERSION_NAME).onSuccess {
                it.forEach { adConstant ->
                    Timber.d("adConstant: $adConstant")
                    setAdConstantUseCase(adConstant.name, adConstant.id)
                }
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    fun onUpdateAlbum(album: List<AlbumItem>) {
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
