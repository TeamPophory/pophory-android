package com.teampophory.pophory.feature.setting

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jakewharton.processphoenix.ProcessPhoenix
import com.teampophory.pophory.config.di.qualifier.Kakao
import com.teampophory.pophory.design.PophoryTheme
import com.teampophory.pophory.feature.auth.social.OAuthService
import com.teampophory.pophory.feature.onboarding.OnBoardingActivity
import com.teampophory.pophory.feature.setting.webview.WebViewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {
    @Inject
    @Kakao
    lateinit var kakaoAuthService: OAuthService

    private val viewModel by viewModels<SettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val message by viewModel.message.collectAsStateWithLifecycle("")
            val navController = rememberNavController()
            PophoryTheme {
                NavHost(navController = navController, startDestination = "setting") {
                    composable("setting") {
                        SettingScreen(
                            message = message,
                            onNavigateHome = { finish() },
                            onNavigateNotice = {
                                startActivity(
                                    WebViewActivity.newIntent(
                                        this@SettingActivity,
                                        "https://pophoryofficial.wixsite.com/pophory/notice"
                                    )
                                )
                            },
                            onLogout = ::logout,
                            onWithdrawal = ::withdrawal
                        )
                    }
                    composable("term") {
                        TermScreen(
                            onNavigatePersonalTerms = {
                                startActivity(
                                    WebViewActivity.newIntent(
                                        this@SettingActivity,
                                        "https://pophoryofficial.wixsite.com/pophory/%EC%A0%95%EC%B1%85#policy2"
                                    )
                                )
                            },
                            onNavigateTerm = {
                                startActivity(
                                    WebViewActivity.newIntent(
                                        this@SettingActivity,
                                        "https://pophoryofficial.wixsite.com/pophory/%EC%A0%95%EC%B1%85#policy1"
                                    )
                                )
                            },
                        )
                    }
                }
            }
        }
        viewModel.event
            .flowWithLifecycle(lifecycle)
            .onEach {
                ProcessPhoenix.triggerRebirth(this, Intent(this, OnBoardingActivity::class.java))
            }.launchIn(lifecycleScope)
    }

    private fun logout() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                runCatching {
                    kakaoAuthService.logout()
                }.onSuccess {
                    viewModel.logout()
                }
            }
        }
    }

    private fun withdrawal() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                runCatching {
                    kakaoAuthService.withdraw()
                }.onSuccess {
                    viewModel.withdrawal()
                }
            }
        }
    }
}
