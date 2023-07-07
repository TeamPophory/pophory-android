package com.teampophory.pophory.feature.album.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.albumsort.AlbumSortType
import com.teampophory.pophory.data.repository.photo.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _albumId = MutableStateFlow(0)
    val albumId: StateFlow<Int> = _albumId

    private val _albumSortType = MutableStateFlow(AlbumSortType.NEWEST)
    val albumSortType: StateFlow<AlbumSortType> get() = _albumSortType

    private val _albumListState = MutableStateFlow<AlbumListState>(AlbumListState.Uninitialized)
    val albumListState: StateFlow<AlbumListState> get() = _albumListState

    fun setAlbumId(id: Int) {
        _albumId.value = id
    }

    fun getAlbums() {
        viewModelScope.launch {
            _albumListState.emit(AlbumListState.Loading)
            photoRepository.getPhotos(albumId.value)
                .onSuccess {
                    _albumListState.emit(AlbumListState.SuccessLoadAlbums(it.mapPhotosToPhotoItems()))
                }.onFailure {
                    Timber.e(it)
                    _albumListState.emit(AlbumListState.Error(it))
                }
        }
    }

    fun sortPhotoList(newest: AlbumSortType) {
        if (albumListState.value is AlbumListState.SuccessLoadAlbums) {
            val photoItems = (albumListState.value as? AlbumListState.SuccessLoadAlbums)?.data
            val sortedPhotoItems = when (newest) {
                AlbumSortType.NEWEST -> photoItems?.sortedByDescending { it.takenAt }
                AlbumSortType.OLDEST -> photoItems?.sortedBy { it.takenAt }
            } ?: run {
                AlbumListState.Error(Throwable("Sort Error"))
                return
            }
            _albumSortType.value = newest
            _albumListState.value = AlbumListState.SuccessLoadAlbums(sortedPhotoItems)
        } else {
            getAlbums()
        }
    }
}