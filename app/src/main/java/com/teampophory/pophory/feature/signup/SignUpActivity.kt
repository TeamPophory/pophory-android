package com.teampophory.pophory.feature.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.teampophory.pophory.common.context.toast
import com.teampophory.pophory.databinding.ActivitySignUpBinding
import com.teampophory.pophory.feature.on_boarding.OnBoardingActivity
import com.teampophory.pophory.feature.signup.adapter.SignUpViewPagerAdapter

class SignUpActivity : AppCompatActivity(), SignUpButtonInterface {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: SignUpViewPagerAdapter

    private val viewModel by viewModels<SignUpViewModel>()
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.sampleLiveData.observe(this) {
            toast(it)
        }

        //다음 버튼
        clickNextButton()
        //뷰페이저
        setViewPager()
        //백버튼 클릭
        clickToolbarBackButton()
    }

    private fun clickToolbarBackButton() {
        val backPosition = currentPosition - 1
        binding.btnBack.setOnClickListener {
            when (currentPosition) {
                0 -> {
                    val intent = Intent(this, OnBoardingActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                1 -> {
                    binding.viewpager.currentItem = backPosition
                }
                2 -> {
                    binding.viewpager.currentItem = backPosition
                }
            }
        }
    }


    private fun clickNextButton() {
        binding.btnNext.setOnClickListener {
            when (currentPosition) {
                0 -> {
                    val nextPosition = currentPosition + 1
                    binding.viewpager.currentItem = nextPosition
                }

                1 -> {
                    //todo 중복된 아이디가 존재하는 경우
                    val dialog = SignUpDialogFragment()
                    dialog.show(supportFragmentManager, "")
                    //중복된 아이디가 존재하지 않을 경우
//                    val nextPosition = currentPosition + 1
//                    binding.viewpager.currentItem = nextPosition
                }

                2 -> {
                    val intent = Intent(this, StartPophoryActivity::class.java)
                    startActivity(intent)
                }
            }
        }
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
            //tablayout 변경
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
        viewPagerAdapter = SignUpViewPagerAdapter(this, this)
        viewPager = binding.viewpager

        viewPager.apply {
            adapter = viewPagerAdapter
//            isUserInputEnabled = false
        }
        viewPager.registerOnPageChangeCallback(pageChangeCallback)
    }

    private fun setButton(buttonText: String, isEnabled: Boolean) {
        binding.btnNext.text = buttonText
        binding.btnNext.isEnabled = isEnabled
    }

    override fun setButtonState(state: Boolean) {
        binding.btnNext.isEnabled = state
    }
}