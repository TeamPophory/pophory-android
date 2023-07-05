package com.teampophory.pophory.feature.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.data.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class Event {
    LOGOUT,
    WITHDRAWAL;
}

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    fun logout() {
        viewModelScope.launch {
            runCatching {
                repository.logout()
            }.onSuccess {
                _event.emit(Event.LOGOUT)
            }.onFailure {
                _message.emit("네트워크 연결상황이 좋지 않아 로그아웃에 실패했습니다.")
            }
        }
    }

    fun withdrawal() {
        viewModelScope.launch {
            runCatching {
                repository.withdraw()
            }.onSuccess {
                _event.emit(Event.WITHDRAWAL)
            }.onFailure {
                _message.emit("네트워크 연결상황이 좋지 않아 회원탈퇴에 실패했습니다.")
            }
        }
    }
}
