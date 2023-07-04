package com.teampophory.pophory.feature.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {

    private val _sampleLiveData = MutableLiveData<String>()
    val sampleLiveData : LiveData<String>get() = _sampleLiveData

    fun getToastMessage() {
        _sampleLiveData.value = "테스트 입니다."
    }
}