package com.teampophory.pophory.feature.sign_up

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.teampophory.pophory.databinding.ActivitySignUpBinding
import com.teampophory.pophory.feature.sign_up.adapter.SignUpViewPagerAdapter

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignUpBinding
    private lateinit var viewPager : ViewPager2
    private lateinit var viewPagerAdapter : SignUpViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
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

            binding.tabFirst.isSelected = position == 0
            binding.tabSecond.isSelected = position == 1
            binding.tabThird.isSelected = position == 2

        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            if(position == 2) {
                binding.btnNext.text = "완료하기"
                binding.btnNext.isEnabled = true

                clickCompleteButton()

            } else {
                binding.btnNext.text = "다음으로 넘어가기"
                binding.btnNext.isEnabled = false
            }

        }
    }

    private fun clickCompleteButton() {

    }

    private fun setViewPager() {
        viewPagerAdapter = SignUpViewPagerAdapter(this)
        viewPager = binding.viewpager

        viewPager.apply {
            adapter = viewPagerAdapter
            //isUserInputEnabled = false
        }
        viewPager.registerOnPageChangeCallback(pageChangeCallback)
    }
}