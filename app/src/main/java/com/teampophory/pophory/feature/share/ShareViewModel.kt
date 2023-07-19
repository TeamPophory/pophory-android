package com.teampophory.pophory.feature.share

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.data.repository.share.ShareRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShareViewModel @Inject constructor(
    private val shareRepository: ShareRepository
) : ViewModel() {

    private val _photos = MutableLiveData<ShareState>(ShareState.Uninitialized)
    val photos: LiveData<ShareState> get() = _photos

    var selectedPosition: Int? = null

    init {
        getPhotos()
    }

    fun getPhotos() {
        viewModelScope.launch {
            _photos.value = ShareState.Loading
            shareRepository.getPhotos()
                .onSuccess {
                    _photos.value =
                        ShareState.SuccessPhoto(it.toPhotos())
                }.onFailure {
                    _photos.value = ShareState.Error(it)
                }
        }
    }
}