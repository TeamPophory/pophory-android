package com.teampophory.pophory.feature.home.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teampophory.pophory.R

class StoreViewModel : ViewModel() {

    private val _photoList = MutableLiveData<List<Int>>()

    val photoList: LiveData<List<Int>> = _photoList

    val mockAlbumList = listOf(
        R.drawable.img_album_cover
    )

    init {
        _photoList.value = mockAlbumList
    }
}