package com.teampophory.pophory.feature.album.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.feature.album.albumsort.AlbumSortType
import com.teampophory.pophory.data.repository.photo.PhotoRepository
import com.teampophory.pophory.feature.album.model.OrientType
import com.teampophory.pophory.feature.album.model.PhotoDetail
import com.teampophory.pophory.feature.album.model.PhotoItem
import com.teampophory.pophory.feature.album.model.mapPhotoItemsToPhotoDetails
import com.teampophory.pophory.feature.home.store.model.AlbumItem
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

    private val _currentAlbum = MutableStateFlow<AlbumItem?>(null)
    val currentAlbum: StateFlow<AlbumItem?> get() = _currentAlbum

    fun onUpdateAlbum(album: AlbumItem?) {
        viewModelScope.launch {
            _currentAlbum.emit(album)
        }
    }

    fun setAlbumId(id: Int) {
        _albumId.value = id
    }

    fun getAlbums() {
        viewModelScope.launch {
            _albumListState.emit(AlbumListState.Loading)
            photoRepository.getPhotos(albumId.value)
                .onSuccess {
                    val photoItems = it.mapPhotosToPhotoItems()
                    _albumListState.emit(AlbumListState.SuccessLoadAlbums(processPhotoDetails(photoItems)))
                }.onFailure {
                    Timber.e(it)
                    _albumListState.emit(AlbumListState.Error(it))
                }
        }
    }

    fun sortPhotoList(sortType: AlbumSortType) {
        if (albumListState.value is AlbumListState.SuccessLoadAlbums) {
            val photoItems = (albumListState.value as? AlbumListState.SuccessLoadAlbums)?.data?.mapPhotoItemsToPhotoDetails()
            val sortedPhotoItems = when (sortType) {
                AlbumSortType.NEWEST -> photoItems?.sortedByDescending { it.takenAt }
                AlbumSortType.OLDEST -> photoItems?.sortedBy { it.takenAt }
            } ?: run {
                AlbumListState.Error(Throwable("Sort Error"))
                return
            }
            _albumSortType.value = sortType
            _albumListState.value = AlbumListState.SuccessLoadAlbums(processPhotoDetails(sortedPhotoItems))
        } else {
            getAlbums()
        }
    }


    private fun processPhotoDetails(photoDetails: List<PhotoDetail>): List<PhotoItem> {
        val photoItems = mutableListOf<PhotoItem>()
        val verticalItemsBuffer = mutableListOf<PhotoDetail>()

        photoDetails.forEach { photoDetail ->
            when (photoDetail.orientType) {
                OrientType.VERTICAL -> {
                    verticalItemsBuffer.add(photoDetail)
                    if (verticalItemsBuffer.size == 2) {
                        photoItems.add(PhotoItem.VerticalItem(verticalItemsBuffer.toList()))
                        verticalItemsBuffer.clear()
                    }
                }

                OrientType.HORIZONTAL -> {
                    if (verticalItemsBuffer.isNotEmpty()) {
                        verticalItemsBuffer.add(createEmptyPhotoDetail())
                        photoItems.add(PhotoItem.VerticalItem(verticalItemsBuffer.toList()))
                        verticalItemsBuffer.clear()
                    }
                    photoItems.add(PhotoItem.HorizontalItem(photoDetail))
                }

                OrientType.NONE -> {

                }
            }
        }

        // 마지막에 VERTICAL 타입의 사진이 한 개만 남아 있는 경우를 처리
        if (verticalItemsBuffer.isNotEmpty()) {
            verticalItemsBuffer.add(createEmptyPhotoDetail())
            photoItems.add(PhotoItem.VerticalItem(verticalItemsBuffer.toList()))
        }
        return photoItems
    }

    private fun createEmptyPhotoDetail(): PhotoDetail {
        return PhotoDetail(0, "", "", "", 0, 0, OrientType.NONE)
    }

}