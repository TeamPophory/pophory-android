package com.teampophory.pophory.feature.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.teampophory.pophory.common.context.toast
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivitySignUpBinding
import com.teampophory.pophory.feature.home.HomeActivity
import com.teampophory.pophory.feature.onboarding.OnBoardingActivity
import com.teampophory.pophory.feature.signup.adapter.SignUpViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity(), SignUpButtonInterface {

    private val binding by viewBinding(ActivitySignUpBinding::inflate)

    private val viewModel by viewModels<SignUpViewModel>()
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.signUpResult.observe(this) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity((intent))
        }

        viewModel.nicknameCheckResult.observe(this) {it ->
            if(!it.isDuplicated) {
                // TODO 중복된 아이디가 존재하지 않을 경우
                val nextPosition = currentPosition + 1
                binding.viewpager.currentItem = nextPosition
            } else {
                // TODO 중복된 아이디가 존재하는 경우
                val dialog = SignUpDialogFragment()
                supportFragmentManager.beginTransaction().add(dialog, "")
                    .commitAllowingStateLoss()
            }
        }
        // 다음 버튼
        clickNextButton()
        // 뷰페이저
        setViewPager()
        // 백버튼 클릭
        clickToolbarBackButton()
    }


    private fun clickToolbarBackButton() {
        binding.btnBack.setOnClickListener {
            when (currentPosition) {
                0 -> {
                    val intent = Intent(this, OnBoardingActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                1 -> {
                    binding.viewpager.currentItem = 0
                }

                2 -> {
                    binding.viewpager.currentItem = 1
                }
            }
        }
    }


    private fun clickNextButton() {
        binding.btnNext.setOnClickListener {
            when (currentPosition) {
                0 -> {
                    binding.viewpager.currentItem = currentPosition + 1
                }

                1 -> {
                    viewModel.nicknameCheck()
                }

                2 -> {
                    viewModel.signUp()
                }
            }
        }
    }


    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            // tablayout 변경
            with(binding) {
                tabFirst.isSelected = position == 0
                tabSecond.isSelected = position == 1
                tabThird.isSelected = position == 2
            }

        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            currentPosition = position

            if (position == 2) {
                setButton("완료하기", true)
            } else {
                setButton("다음으로 넘어가기", false)
            }
        }
    }

    private fun setViewPager() {
        binding.viewpager.apply {
            adapter = SignUpViewPagerAdapter(this@SignUpActivity, this@SignUpActivity)
            isUserInputEnabled = false
        }
        binding.viewpager.registerOnPageChangeCallback(pageChangeCallback)
    }

    private fun setButton(buttonText: String, isEnabled: Boolean) {
        binding.btnNext.text = buttonText
        binding.btnNext.isEnabled = isEnabled
    }

    override fun onChangeState(state: Boolean) {
        binding.btnNext.isEnabled = state
    }
}