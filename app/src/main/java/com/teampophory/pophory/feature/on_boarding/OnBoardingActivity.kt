package com.teampophory.pophory.feature.on_boarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewpager2.widget.ViewPager2
import com.teampophory.pophory.databinding.ActivityOnBoardingBinding
import com.teampophory.pophory.feature.on_boarding.adapter.OnBoardingViewPagerAdapter
import com.teampophory.pophory.feature.signup.SignUpActivity

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: OnBoardingViewPagerAdapter

    private lateinit var splashScreen: SplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setViewPager()

        clickKakaoLoginBtn()
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)

            binding.dotIndicatorFirst.isSelected = position == 0
            binding.dotIndicatorSecond.isSelected = position == 1
            binding.dotIndicatorThird.isSelected = position == 2

        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

        }
    }

    //todo 카카오 로그인으로 수정 예정
    private fun clickKakaoLoginBtn() {
        binding.btnStartSocialLogin.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setViewPager() {
        viewPager = binding.viewpagerOnboarding
        adapter = OnBoardingViewPagerAdapter()

        adapter.submitList(
            mutableListOf(
                OnBoardingData("#8BC34A"),
                OnBoardingData("#FF03DAC5"),
                OnBoardingData("#FFBB86FC")
            )
        )
        viewPager.adapter = adapter
        viewPager.registerOnPageChangeCallback(pageChangeCallback)
    }
}