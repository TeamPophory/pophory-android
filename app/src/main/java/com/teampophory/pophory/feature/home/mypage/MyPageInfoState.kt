package com.teampophory.pophory.feature.home.mypage

sealed class MyPageInfoState {
    object Uninitialized : MyPageInfoState()
    object Loading : MyPageInfoState()
    data class SuccessMyPageInfo(val data: List<MyPageDisplayItem>) : MyPageInfoState()
    data class Error(val error: Throwable) : MyPageInfoState()
}
