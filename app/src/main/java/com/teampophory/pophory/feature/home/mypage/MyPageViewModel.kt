package com.teampophory.pophory.feature.home.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.data.repository.my.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository
) : ViewModel() {

    private val _myPageUserInfo = MutableLiveData<MyPageInfoState>(MyPageInfoState.Uninitialized)
    val myPageInfo: LiveData<MyPageInfoState> get() = _myPageUserInfo

    fun getMyPageInfo() {
        viewModelScope.launch {
            _myPageUserInfo.value = MyPageInfoState.Loading
            myPageRepository.getMyPageInfo()
                .onSuccess {
                    _myPageUserInfo.value =
                        MyPageInfoState.SuccessMyPageInfo(it.toItems())
                }.onFailure {
                    _myPageUserInfo.value = MyPageInfoState.Error(it)
                }
        }
    }
}