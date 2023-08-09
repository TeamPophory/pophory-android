package com.teampophory.pophory.feature.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teampophory.pophory.auth.usecase.AutoLoginConfigureUseCase
import com.teampophory.pophory.data.network.model.auth.NicknameRequest
import com.teampophory.pophory.data.network.model.auth.NicknameResponse
import com.teampophory.pophory.data.network.model.auth.SignUpRequest
import com.teampophory.pophory.data.network.model.auth.SignUpResponse
import com.teampophory.pophory.data.network.service.RetrofitSignUpNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpService: RetrofitSignUpNetwork,
    private val autoLoginConfigureUseCase: AutoLoginConfigureUseCase
) : ViewModel() {

    private val _signUpResult: MutableLiveData<SignUpResponse> = MutableLiveData()
    val signUpResult: LiveData<SignUpResponse> = _signUpResult

    private val _nicknameCheckResult: MutableLiveData<NicknameResponse> = MutableLiveData()
    val nicknameCheckResult: LiveData<NicknameResponse> = _nicknameCheckResult

    private val _buttonState: MutableLiveData<Boolean> = MutableLiveData()
    val buttonState: LiveData<Boolean> = _buttonState

    private val _realName: MutableLiveData<String> = MutableLiveData()
    var realName: LiveData<String> = _realName

    private val _nickName: MutableLiveData<String> = MutableLiveData()
    var nickName: LiveData<String> = _nickName

    private val _albumCover: MutableLiveData<Int> = MutableLiveData(1)
    var albumCover: LiveData<Int> = _albumCover

    fun setButtonState(state: Boolean) {
        _buttonState.value = state
    }

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
                albumCover.value ?: 1
            )
        ).enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                if (response.isSuccessful) {
                    autoLoginConfigureUseCase(true)
                    _signUpResult.value = response.body()
                } else {
                    Timber.e(response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Timber.e(t)
            }
        })
    }

    fun nicknameCheck() {
        signUpService.nicknameCheck(
            NicknameRequest(
                nickName.value.orEmpty()
            )
        ).enqueue(object : Callback<NicknameResponse> {
            override fun onResponse(
                call: Call<NicknameResponse>,
                response: Response<NicknameResponse>
            ) {
                if (response.isSuccessful) {
                    _nicknameCheckResult.value = response.body()
                } else {
                    Timber.e(response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<NicknameResponse>, t: Throwable) {
                Timber.e(t)
            }
        })
    }

}
