package com.teampophory.pophory.feature.home.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teampophory.pophory.R

class StoreViewModel : ViewModel() {

    private val _albumList = MutableLiveData<List<Int>>()

    val albumList: LiveData<List<Int>> get() = _albumList

    val mockAlbumList = listOf(
        R.drawable.img_album_cover
    )

    init {
        _albumList.value = mockAlbumList
    }
}