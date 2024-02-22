package com.teampophory.pophory.feature.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.auth.repository.AuthRepository
import com.teampophory.pophory.auth.usecase.AutoLoginConfigureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val autoLoginConfigureUseCase: AutoLoginConfigureUseCase,
) : ViewModel() {

    private val _signUpResult: MutableLiveData<Int> = MutableLiveData()
    val signUpResult: LiveData<Int> = _signUpResult

    private val _nicknameCheckResult: MutableLiveData<Boolean> = MutableLiveData()
    val nicknameCheckResult: LiveData<Boolean> = _nicknameCheckResult

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
        viewModelScope.launch {
            runCatching {
                repository.signUp(
                    realName.value.orEmpty(),
                    nickName.value.orEmpty(),
                    albumCover.value ?: 1,
                )
            }.onSuccess {
                autoLoginConfigureUseCase(true)
                _signUpResult.value = it
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    fun nicknameCheck() {
        viewModelScope.launch {
            runCatching {
                repository.validateNickname(nickName.value.orEmpty())
            }.onSuccess {
                _nicknameCheckResult.value = it
            }.onFailure {
                Timber.e(it)
            }
        }
    }
}
