package com.teampophory.pophory.feature.home.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.data.repository.PhotoRepository
import com.teampophory.pophory.data.repository.fake.FakeMyPageInfoRepository
import com.teampophory.pophory.network.model.toMyPageInfo
import kotlinx.coroutines.launch
import timber.log.Timber


class MyPageViewModel : ViewModel() {

    private val myPageInfoRepository: PhotoRepository = FakeMyPageInfoRepository()

    private val _myPageUserInfo = MutableLiveData<MyPageInfoState>(MyPageInfoState.Uninitialized)
    val photoList: LiveData<MyPageInfoState> get() = _myPageUserInfo

    fun getMyPageInfo() {
        viewModelScope.launch {
            _myPageUserInfo.value = MyPageInfoState.Loading
            myPageInfoRepository.getMyPageInfo()
                .onSuccess {
                    _myPageUserInfo.value = MyPageInfoState.SuccessPhotos(it.toMyPageInfo())
                }.onFailure {
                    _myPageUserInfo.value = MyPageInfoState.Error(it)
                }
        }
    }

    init {
        getMyPageInfo()
    }
}