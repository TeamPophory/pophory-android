package com.teampophory.pophory.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.auth.entity.UserAccountState
import com.teampophory.pophory.auth.usecase.AuthUseCase
import com.teampophory.pophory.auth.usecase.AutoLoginConfigureUseCase
import com.teampophory.pophory.auth.usecase.GetAutoLoginConfigurationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

sealed interface Effect {
    data object Home : Effect
    data object SignUp : Effect
    data class Snackbar(val message: String) : Effect
}

data class OnboardingUiState(
    val isAutoLoginEnabled: Boolean = false
)

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val autoLoginConfigureUseCase: AutoLoginConfigureUseCase,
    autoLoginConfigurationUseCase: GetAutoLoginConfigurationUseCase
) : ViewModel() {
    private val _event = MutableSharedFlow<Effect>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow(OnboardingUiState())
    val state = _state.asStateFlow()

    init {
        _state.value = OnboardingUiState(autoLoginConfigurationUseCase())
    }

    fun onLogin(token: String) {
        viewModelScope.launch {
            authUseCase(token)
                .onSuccess { state ->
                    when (state) {
                        UserAccountState.REGISTERED -> {
                            autoLoginConfigureUseCase(true)
                            _event.emit(Effect.Home)
                        }

                        UserAccountState.UNREGISTERED -> {
                            _event.emit(Effect.SignUp)
                        }
                    }

                }.onFailure {
                    Timber.e(it)
                    _event.emit(Effect.Snackbar("로그인에 실패했습니다."))
                }
        }
    }
}
