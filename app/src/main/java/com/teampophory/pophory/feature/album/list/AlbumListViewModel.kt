package com.teampophory.pophory.feature.album.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.data.repository.photo.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    private val photoRepository: AlbumRepository
) : ViewModel() {

    private val _albumList = MutableLiveData<AlbumListState>(AlbumListState.Uninitialized)
    val albumList: LiveData<AlbumListState> get() = _albumList

    fun getAlbums(id: Int) {
        viewModelScope.launch {
            _albumList.value = AlbumListState.Loading
            photoRepository.getPhotos(id)
                .onSuccess {
                    _albumList.value = AlbumListState.SuccessAlbums(it.mapPhotosToPhotoItems())
                }.onFailure {
                    Timber.e(it)
                    _albumList.value = AlbumListState.Error(it)
                }
        }
    }
}