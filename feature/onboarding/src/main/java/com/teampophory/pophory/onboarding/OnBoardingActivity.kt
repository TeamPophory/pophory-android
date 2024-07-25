package com.teampophory.pophory.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.teampophory.pophory.common.context.snackBar
import com.teampophory.pophory.common.navigation.NavigationProvider
import com.teampophory.pophory.common.qualifier.Kakao
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.designsystem.dialog.DialogUtil
import com.teampophory.pophory.feature.auth.social.OAuthService
import com.teampophory.pophory.onboarding.adapter.OnBoardingViewPagerAdapter
import com.teampophory.pophory.onboarding.databinding.ActivityOnBoardingBinding
import com.teampophory.pophory.onboarding.model.OnBoardingData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityOnBoardingBinding::inflate)
    private val viewModel by viewModels<OnBoardingViewModel>()

    @Inject
    @Kakao
    lateinit var kakaoAuthService: OAuthService

    @Inject
    lateinit var navigationProvider: NavigationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle)
                .onEach { state ->
                    if (state.isUpdateRequired) {
                        showUpdateDialog()
                    } else {
                        collectState()
                        setViewPager()
                        setOnLoginPressed()
                        collectEvent()
                    }
                }
                .launchIn(lifecycleScope)
        }
    }

    private fun showUpdateDialog() {
        DialogUtil.showForceUpdateDialog(
            context = this,
            supportFragmentManager = supportFragmentManager,
            title = "업데이트 필요",
            description = "새로운 버전이 필요합니다. 업데이트 해주세요.",
            buttonText = "업데이트",
            imageResId = R.drawable.ic_customizing_done
        )
    }

    private fun collectState() {
        viewModel.state
            .flowWithLifecycle(lifecycle)
            .onEach {
                if (it.isAutoLoginEnabled) {
                    startActivity(navigationProvider.toHome())
                }
            }.launchIn(lifecycleScope)
    }

    private fun collectEvent() {
        viewModel.event
            .flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is Effect.Home -> {
                        startActivity(navigationProvider.toHome())
                    }

                    is Effect.SignUp -> {
                        startActivity(navigationProvider.toSignUp())
                    }

                    is Effect.Snackbar -> {
                        snackBar(binding.root) { it.message }
                    }
                }
            }.launchIn(lifecycleScope)
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int,
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            binding.dotIndicatorFirst.isSelected = position == 0
            binding.dotIndicatorSecond.isSelected = position == 1
            binding.dotIndicatorThird.isSelected = position == 2
            binding.dotIndicatorFourth.isSelected = position == 3
        }
    }

    private fun setOnLoginPressed() {
        binding.layoutBtnKakao.setOnClickListener {
            lifecycleScope.launch {
                runCatching {
                    kakaoAuthService.login()
                }.onSuccess { token ->
                    viewModel.onLogin(token.accessToken)
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
                OnBoardingData(R.drawable.img_onboarding04),
            ),
        )
        binding.viewpagerOnboarding.adapter = adapter
        binding.viewpagerOnboarding.registerOnPageChangeCallback(pageChangeCallback)
    }

    companion object {
        @JvmStatic
        fun newInstance(context: Context) = Intent(context, OnBoardingActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    }
}

