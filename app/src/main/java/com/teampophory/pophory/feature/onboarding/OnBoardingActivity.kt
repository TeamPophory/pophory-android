package com.teampophory.pophory.feature.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.teampophory.pophory.R
import com.teampophory.pophory.common.context.snackBar
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.config.di.qualifier.Kakao
import com.teampophory.pophory.data.local.PophoryDataStore
import com.teampophory.pophory.data.model.auth.UserAccountState
import com.teampophory.pophory.databinding.ActivityOnBoardingBinding
import com.teampophory.pophory.domain.AuthUseCase
import com.teampophory.pophory.domain.AutoLoginConfigureUseCase
import com.teampophory.pophory.feature.auth.social.OAuthService
import com.teampophory.pophory.feature.home.HomeActivity
import com.teampophory.pophory.feature.onboarding.adapter.OnBoardingViewPagerAdapter
import com.teampophory.pophory.feature.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityOnBoardingBinding::inflate)

    @Inject
    @Kakao
    lateinit var kakaoAuthService: OAuthService

    @Inject
    lateinit var authUseCase: AuthUseCase

    @Inject
    lateinit var autoLoginConfigureUseCase: AutoLoginConfigureUseCase

    @Inject
    lateinit var dataStore: PophoryDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        if (dataStore.autoLoginConfigured) {
            startActivity(HomeActivity.getIntent(this@OnBoardingActivity))
        }
        setContentView(binding.root)
        setViewPager()
        setOnLoginPressed()
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            binding.dotIndicatorFirst.isSelected = position == 0
            binding.dotIndicatorSecond.isSelected = position == 1
            binding.dotIndicatorThird.isSelected = position == 2
            binding.dotIndicatorFourth.isSelected = position == 3
        }
    }

    private fun setOnLoginPressed() {
        binding.btnStartSocialLogin.setOnClickListener {
            lifecycleScope.launch {
                runCatching {
                    kakaoAuthService.login()
                }.onSuccess { token ->
                    authUseCase(token.accessToken)
                        .onSuccess { state ->
                            when (state) {
                                UserAccountState.REGISTERED -> {
                                    autoLoginConfigureUseCase(true)
                                    startActivity(HomeActivity.getIntent(this@OnBoardingActivity))
                                }

                                UserAccountState.UNREGISTERED -> {
                                    val intent =
                                        Intent(this@OnBoardingActivity, SignUpActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                        }
                        .onFailure {
                            Timber.e(it)
                            snackBar(binding.root) { "로그인에 실패했습니다." }
                        }
                }.onFailure(Timber::e)
            }
        }
    }

    private fun setViewPager() {
        val adapter = OnBoardingViewPagerAdapter()

        adapter.submitList(
            mutableListOf(
                OnBoardingData(R.drawable.img_onboarding01),
                OnBoardingData(R.drawable.img_onboarding02),
                OnBoardingData(R.drawable.img_onboarding03),
                OnBoardingData(R.drawable.img_onboarding04)
            )
        )
        binding.viewpagerOnboarding.adapter = adapter
        binding.viewpagerOnboarding.registerOnPageChangeCallback(pageChangeCallback)
    }
}