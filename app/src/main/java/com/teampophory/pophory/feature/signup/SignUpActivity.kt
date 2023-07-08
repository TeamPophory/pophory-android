package com.teampophory.pophory.feature.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.viewpager2.widget.ViewPager2
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivitySignUpBinding
import com.teampophory.pophory.feature.onboarding.OnBoardingActivity
import com.teampophory.pophory.feature.signup.adapter.SignUpViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySignUpBinding::inflate)

    private val viewModel by viewModels<SignUpViewModel>()
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickNextButton()
        setViewPager()
        setOnBackPressed()
        observeEvent()
    }

    private fun observeEvent() {
        viewModel.signUpResult.observe(this) {
            startActivity(StartPophoryActivity.getIntent(this))
        }

        viewModel.nicknameCheckResult.observe(this) {
            if (!it.isDuplicated) {
                val nextPosition = currentPosition + 1
                binding.viewpager.currentItem = nextPosition
            } else {
                supportFragmentManager.commit(allowStateLoss = true) {
                    add(SignUpDialogFragment(), SignUpDialogFragment::class.java.simpleName)
                }
            }
        }
    }

    private fun setOnBackPressed() {
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


    private fun setOnClickNextButton() {
        binding.btnNext.setOnClickListener {
            when (currentPosition) {
                0 -> binding.viewpager.currentItem = currentPosition + 1
                1 -> viewModel.nicknameCheck()
                2 -> viewModel.signUp()
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
            with(binding) {
                tabFirst.isSelected = position == 0
                tabSecond.isSelected = position == 1
                tabThird.isSelected = position == 2
            }

        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            currentPosition = position
            binding.btnNext.text = if (position == 2) "완료하기" else "다음으로 넘어가기"
            binding.btnNext.isEnabled = position == 2
        }
    }

    private fun setViewPager() {
        binding.viewpager.apply {
            adapter = SignUpViewPagerAdapter(this@SignUpActivity) {
                binding.btnNext.isEnabled = it
            }
            isUserInputEnabled = false
        }
        binding.viewpager.registerOnPageChangeCallback(pageChangeCallback)
    }
}