package com.teampophory.pophory.feature.home.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teampophory.pophory.R

class StoreViewModel : ViewModel() {

    private val _albumList = MutableLiveData<List<Int>>()

    val albumList: LiveData<List<Int>> get() = _albumList

    val albumCoverList = listOf(
        R.drawable.ic_album_cover_friends
    )

    init {
        _albumList.value = albumCoverList
    }
}