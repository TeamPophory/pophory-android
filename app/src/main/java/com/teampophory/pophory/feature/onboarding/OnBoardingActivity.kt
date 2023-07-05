package com.teampophory.pophory.feature.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewpager2.widget.ViewPager2
import com.teampophory.pophory.R
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityOnBoardingBinding
import com.teampophory.pophory.feature.onboarding.adapter.OnBoardingViewPagerAdapter
import com.teampophory.pophory.feature.signup.SignUpActivity

class OnBoardingActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityOnBoardingBinding::inflate)
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: OnBoardingViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

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

        //todo 이미지로 수정 예정
        adapter.submitList(
            mutableListOf(
                OnBoardingData(getColorAsString(applicationContext, R.color.pophory_red)),
                OnBoardingData(getColorAsString(applicationContext, R.color.pophory_purple)),
                OnBoardingData(getColorAsString(applicationContext, R.color.pophory_red))
            )
        )
        viewPager.adapter = adapter
        viewPager.registerOnPageChangeCallback(pageChangeCallback)
    }

    private fun getColorAsString(context: Context, colorResourceId: Int): String {
        val colorValue: Int = ContextCompat.getColor(context, colorResourceId)
        return String.format("#%06X", 0xFFFFFF and colorValue)
    }
}