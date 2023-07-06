package com.teampophory.pophory.feature.home.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.data.repository.store.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val storeRepository: StoreRepository
) : ViewModel() {

    private val _albums = MutableLiveData<StoreState>(StoreState.Uninitialized)
    val albums: LiveData<StoreState> get() = _albums

    fun getAlbums() {
        viewModelScope.launch {
            _albums.value = StoreState.Loading
            storeRepository.getAlbums()
                .onSuccess {
                    _albums.value =
                        StoreState.SuccessAlbums(it.toAlbums())
                }.onFailure {
                    _albums.value = StoreState.Error(it)
                }
        }
    }
}