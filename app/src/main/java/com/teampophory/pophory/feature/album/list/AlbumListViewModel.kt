package com.teampophory.pophory.feature.album.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.albumsort.AlbumSortType
import com.teampophory.pophory.data.repository.photo.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    private val photoRepository: AlbumRepository
) : ViewModel() {

    private val _albumId = MutableLiveData(0)
    val albumId: LiveData<Int> get() = _albumId

    private val _albumList = MutableLiveData<AlbumListState>(AlbumListState.Uninitialized)
    val albumList: LiveData<AlbumListState> get() = _albumList

    fun setAlbumId(id: Int) {
        _albumId.value = id
    }

    fun getAlbums() {
        viewModelScope.launch {
            _albumList.value = AlbumListState.Loading
            photoRepository.getPhotos(albumId.value ?: 0)
                .onSuccess {
                    _albumList.value = AlbumListState.SuccessLoadAlbums(it.mapPhotosToPhotoItems())
                }.onFailure {
                    Timber.e(it)
                    _albumList.value = AlbumListState.Error(it)
                }
        }
    }

    fun sortPhotoList(newest: AlbumSortType) {
        if (albumList.value is AlbumListState.SuccessLoadAlbums) {
            val photoItems = (albumList.value as? AlbumListState.SuccessLoadAlbums)?.data
            val sortedPhotoItems = when (newest) {
                AlbumSortType.NEWEST -> photoItems?.sortedByDescending { it.takenAt }
                AlbumSortType.OLDEST -> photoItems?.sortedBy { it.takenAt }
            } ?: run {
                AlbumListState.Error(Throwable("Sort Error"))
                return
            }
            _albumList.value = AlbumListState.SuccessLoadAlbums(sortedPhotoItems)
        } else {
            getAlbums()
        }
    }
}