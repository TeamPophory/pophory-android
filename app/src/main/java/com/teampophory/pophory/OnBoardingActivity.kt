package com.teampophory.pophory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewpager2.widget.ViewPager2
import com.teampophory.pophory.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOnBoardingBinding
    private lateinit var viewPager : ViewPager2
    private lateinit var adapter : OnBoardingViewPagerAdapter

    private lateinit var splashScreen: SplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setViewPager()
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
            when(position) {
                0 -> {
                    binding.dotIndicatorFirst.isSelected = true
                    binding.dotIndicatorSecond.isSelected = false
                    binding.dotIndicatorThird.isSelected = false
                }
                1 -> {
                    binding.dotIndicatorFirst.isSelected = false
                    binding.dotIndicatorSecond.isSelected = true
                    binding.dotIndicatorThird.isSelected = false
                }
                2 -> {
                    binding.dotIndicatorFirst.isSelected = false
                    binding.dotIndicatorSecond.isSelected = false
                    binding.dotIndicatorThird.isSelected = true
                }
            }
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

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