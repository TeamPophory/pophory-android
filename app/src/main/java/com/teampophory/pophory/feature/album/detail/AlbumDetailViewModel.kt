package com.teampophory.pophory.feature.album.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.data.repository.photo.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _photoDetailInfo = MutableLiveData<PhotoDetailInfo>()
    val photoDetailInfo: LiveData<PhotoDetailInfo> get() = _photoDetailInfo

    private val _albumDetailState =
        MutableLiveData<AlbumDetailState>(AlbumDetailState.Uninitialized)
    val albumDetailState: LiveData<AlbumDetailState> get() = _albumDetailState

    init {
        setData()
    }

    fun deleteAlbum() {
        val albumId = photoDetailInfo.value?.id?.toLong() ?: 0L
        viewModelScope.launch {
            photoRepository.deletePhoto(albumId).onSuccess {
                _albumDetailState.value = AlbumDetailState.SuccessDeleteAlbum
            }.onFailure {
                _albumDetailState.value = AlbumDetailState.Error(it.message.toString())
            }
        }
    }

    private fun setData() {
        val id = savedStateHandle.get<Int>("id") ?: 0
        val studio = savedStateHandle.get<String>("studio").orEmpty()
        val takenAt = savedStateHandle.get<String>("takenAt").orEmpty()
        val imageUrl = savedStateHandle.get<String>("imageUrl").orEmpty()
        _photoDetailInfo.value = PhotoDetailInfo(id, studio, takenAt, imageUrl)
    }

    data class PhotoDetailInfo(
        var id: Int,
        var studio: String,
        var takenAt: String,
        var imageUrl: String
    )
}