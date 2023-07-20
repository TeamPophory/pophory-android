package com.teampophory.pophory.feature.home.mypage

import com.teampophory.pophory.feature.home.mypage.model.Profile

sealed class MyPageInfoState {
    data object Uninitialized : MyPageInfoState()
    data object Loading : MyPageInfoState()
    data class SuccessMyPageInfo(val data: Profile) : MyPageInfoState()
    data class Error(val error: Throwable) : MyPageInfoState()
}
