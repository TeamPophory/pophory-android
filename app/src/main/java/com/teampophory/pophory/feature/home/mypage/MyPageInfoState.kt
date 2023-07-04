package com.teampophory.pophory.feature.home.mypage

import com.teampophory.pophory.feature.home.mypage.model.MyPageInfo

sealed class MyPageInfoState {
    object Uninitialized : MyPageInfoState()
    object Loading : MyPageInfoState()
    data class SuccessMyPageInfo(val data: MyPageInfo) : MyPageInfoState()
    data class Error(val error: Throwable) : MyPageInfoState()
}