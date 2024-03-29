package com.teampophory.pophory.feature.album.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.domain.repository.photo.PhotoRepository
import com.teampophory.pophory.feature.album.model.PhotoRaw
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _photoDetail = MutableLiveData<PhotoRaw>()
    val photoDetail: LiveData<PhotoRaw> get() = _photoDetail

    private val _albumDetailState =
        MutableLiveData<AlbumDetailState>(AlbumDetailState.Uninitialized)
    val albumDetailState: LiveData<AlbumDetailState> get() = _albumDetailState

    init {
        setData()
    }

    fun deleteAlbum() {
        val albumId = photoDetail.value?.id ?: 0
        viewModelScope.launch {
            photoRepository.deletePhoto(albumId).onSuccess {
                _albumDetailState.value = AlbumDetailState.SuccessDeleteAlbum
            }.onFailure {
                _albumDetailState.value = AlbumDetailState.Error(it.message.toString())
            }
        }
    }

    private fun setData() {
        val id = savedStateHandle.get<Long>("photoId") ?: 0
        val studio = savedStateHandle.get<String>("studio").orEmpty()
        val takenAt = savedStateHandle.get<String>("takenAt").orEmpty()
        val imageUrl = savedStateHandle.get<String>("imageUrl").orEmpty()
        val shareId = savedStateHandle.get<String>("shareId").orEmpty()
        _photoDetail.value = PhotoRaw(id, studio, takenAt, imageUrl, shareId)
    }
}
