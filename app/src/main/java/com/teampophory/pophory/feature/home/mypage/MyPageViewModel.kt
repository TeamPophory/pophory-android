package com.teampophory.pophory.feature.home.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.data.repository.PhotoRepository
import com.teampophory.pophory.data.repository.fake.FakePhotoRepository
import com.teampophory.pophory.network.model.toPhotoList
import kotlinx.coroutines.launch
import timber.log.Timber


class MyPageViewModel : ViewModel() {

    private val photoRepository: PhotoRepository = FakePhotoRepository()

    private val _photoList = MutableLiveData<PhotoState>(PhotoState.Uninitialized)
    val photoList: LiveData<PhotoState> get() = _photoList

    fun getPhotos() {
        viewModelScope.launch {
            _photoList.value = PhotoState.Loading
            photoRepository.getPhotos()
                .onSuccess {
                    _photoList.value = PhotoState.SuccessPhotos(it.toPhotoList())
                }.onFailure {
                    Timber.e(it)
                    _photoList.value = PhotoState.Error(it)
                }
        }
    }

    init {
        getPhotos()
    }
}