package com.teampophory.pophory.feature.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teampophory.pophory.feature.signup.ServicePool.signUpService
import com.teampophory.pophory.network.model.SignUpRequest
import com.teampophory.pophory.network.model.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {

    private val _signUpResult: MutableLiveData<SignUpResponse> = MutableLiveData()
    val signUpResult: LiveData<SignUpResponse> = _signUpResult

    private val _realName: MutableLiveData<String> = MutableLiveData()
    var realName: LiveData<String> = _realName

    private val _nickName: MutableLiveData<String> = MutableLiveData()
    var nickName: LiveData<String> = _nickName

    private val _albumCover: MutableLiveData<Int> = MutableLiveData()
    var albumCover: LiveData<Int> = _albumCover

    fun setRealName(realName: String) {
        _realName.value = realName
    }

    fun setNickName(nickName: String) {
        _nickName.value = nickName
    }

    fun setAlbumCover(albumCover: Int) {
        _albumCover.value = albumCover
    }

    fun signUp() {
        signUpService.signUp(
            SignUpRequest(
                realName.value.orEmpty(),
                nickName.value.orEmpty(),
                albumCover.value ?: 0
            )
        ).enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                if (response.isSuccessful) {
                    _signUpResult.value = response.body()
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}
