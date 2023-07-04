package com.teampophory.pophory.feature.home.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.data.repository.MyPageInfoRepository
import com.teampophory.pophory.data.repository.fake.FakeMyPageInfoRepository
import kotlinx.coroutines.launch


class MyPageViewModel : ViewModel() {

    private val myPageInfoRepository: MyPageInfoRepository = FakeMyPageInfoRepository()

    private val _myPageUserInfo = MutableLiveData<MyPageInfoState>(MyPageInfoState.Uninitialized)
    val myPageInfo: LiveData<MyPageInfoState> get() = _myPageUserInfo

    fun getMyPageInfo() {
        viewModelScope.launch {
            _myPageUserInfo.value = MyPageInfoState.Loading

            myPageInfoRepository.getMyPageInfo()
                .onSuccess {
                    _myPageUserInfo.value =
                        MyPageInfoState.SuccessMyPageInfo(it.toItems())
                }.onFailure {
                    _myPageUserInfo.value = MyPageInfoState.Error(it)
                }
        }
    }
}