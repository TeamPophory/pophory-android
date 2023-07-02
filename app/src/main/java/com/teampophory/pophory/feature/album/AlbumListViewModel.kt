package com.teampophory.pophory.feature.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.data.repository.PhotoRepository
import com.teampophory.pophory.data.repository.fake.FakePhotoRepository
import com.teampophory.pophory.network.model.toPhotoList
import kotlinx.coroutines.launch
import timber.log.Timber

class AlbumListViewModel : ViewModel() {

    private val photoRepository: PhotoRepository = FakePhotoRepository()

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