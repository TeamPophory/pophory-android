package com.teampophory.pophory.feature.album.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.data.repository.photo.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {
    private val _id = MutableLiveData<Int>()
    val id: LiveData<Int> get() = _id

    private val _studio = MutableLiveData<String>()
    val studio: LiveData<String> get() = _studio

    private val _takenAt = MutableLiveData<String>()
    val takenAt: LiveData<String> get() = _takenAt

    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> get() = _imageUrl

    private val _albumDetailState = MutableLiveData<AlbumDetailState>(AlbumDetailState.Uninitialized)
    val albumDetailState: LiveData<AlbumDetailState> get() = _albumDetailState

    fun setPhotoId(photoId: Int) {
        _id.value = photoId
    }

    fun setStudio(studio: String) {
        _studio.value = studio
    }

    fun setTakenAt(takenAt: String) {
        _takenAt.value = takenAt
    }

    fun setImageUrl(imageUrl: String) {
        _imageUrl.value = imageUrl
    }

    fun deleteAlbum() {
        val albumId = id.value?.toLong() ?: 0L
        viewModelScope.launch {
            photoRepository.deletePhoto(albumId).onSuccess {
                _albumDetailState.value = AlbumDetailState.SuccessDeleteAlbum
            }.onFailure {
                _albumDetailState.value = AlbumDetailState.Error(it.message.toString())
            }
        }
    }
}