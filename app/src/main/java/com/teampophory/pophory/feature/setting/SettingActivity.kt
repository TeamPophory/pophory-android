package com.teampophory.pophory.feature.setting

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.teampophory.pophory.config.di.qualifier.Kakao
import com.teampophory.pophory.design.PophoryTheme
import com.teampophory.pophory.feature.auth.social.OAuthService
import com.teampophory.pophory.feature.setting.webview.WebViewActivity
import dagger.hilt.android.AndroidEntryPoint
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
            PophoryTheme {
                SettingScreen(
                    message = message,
                    onNavigateHome = { finish() },
                    onNavigateNotice = {
                        startActivity(
                            WebViewActivity.newIntent(
                                this,
                                "https://pophoryofficial.wixsite.com/pophory/notice"
                            )
                        )
                    },
                    onNavigatePersonalTerms = {
                        startActivity(
                            WebViewActivity.newIntent(
                                this,
                                "https://pophoryofficial.wixsite.com/pophory/gaeinjeongbo-ceoribangcim/%E2%80%8B%EA%B0%9C%E[…]%EC%A0%95%EB%B3%B4-%EC%B2%98%EB%A6%AC%EB%B0%A9%EC%B9%A8"
                            )
                        )
                    },
                    onNavigateTerm = {
                        startActivity(
                            WebViewActivity.newIntent(
                                this,
                                "https://pophoryofficial.wixsite.com/pophory/copy-of-gaeinjeongbo-ceoribangcim/%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%9D%B4%EC%9A%A9-%EC%95%BD%EA%B4%80"
                            )
                        )
                    },
                    onLogout = ::logout,
                )
            }
        }
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
}
