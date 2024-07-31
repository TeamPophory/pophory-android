package com.teampophory.pophory.onboarding

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.auth.entity.UserAccountState
import com.teampophory.pophory.auth.usecase.AuthUseCase
import com.teampophory.pophory.auth.usecase.AutoLoginConfigureUseCase
import com.teampophory.pophory.auth.usecase.GetAutoLoginConfigurationUseCase
import com.teampophory.pophory.config.remote.usecase.CheckAppVersionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    val isAutoLoginEnabled: Boolean = false,
    val isUpdateRequired: Boolean = false
)

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authUseCase: AuthUseCase,
    private val autoLoginConfigureUseCase: AutoLoginConfigureUseCase,
    private val autoLoginConfigurationUseCase: GetAutoLoginConfigurationUseCase,
    private val checkAppVersionUseCase: CheckAppVersionUseCase
) : ViewModel() {
    private val _event = MutableSharedFlow<Effect>()
    val event = _event.asSharedFlow()
    private val _state = MutableStateFlow(OnboardingUiState())
    val state = _state.asStateFlow()

    init {
        checkAppVersion()
    }

    private fun getCurrentVersionName(): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: Exception) {
            "1.4.1"
        }
    }

    private fun checkAppVersion() {
        val currentVersion = getCurrentVersionName()
        viewModelScope.launch {
            try {
                val isUpdateRequired = checkAppVersionUseCase(currentVersion)
                _state.value = OnboardingUiState(
                    isUpdateRequired = isUpdateRequired,
                    isAutoLoginEnabled = !isUpdateRequired && autoLoginConfigurationUseCase()
                )
            } catch (e: Exception) {
                _state.value = OnboardingUiState(
                    isUpdateRequired = false,
                    isAutoLoginEnabled = autoLoginConfigurationUseCase()
                )
            }
        }
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
