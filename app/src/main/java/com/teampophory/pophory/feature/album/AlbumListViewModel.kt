package com.teampophory.pophory.feature.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.data.repository.PhotoRepository
import com.teampophory.pophory.network.model.toPhotoList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _albumList = MutableLiveData<AlbumState>(AlbumState.Uninitialized)
    val albumList: LiveData<AlbumState> get() = _albumList

    fun getAlbums() {
        viewModelScope.launch {
            _albumList.value = AlbumState.Loading
            photoRepository.getPhotos()
                .onSuccess {
                    _albumList.value = AlbumState.SuccessAlbums(it.toPhotoList())
                }.onFailure {
                    Timber.e(it)
                    _albumList.value = AlbumState.Error(it)
                }
        }
    }
}