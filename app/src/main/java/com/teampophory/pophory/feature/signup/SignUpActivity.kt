package com.teampophory.pophory.feature.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.teampophory.pophory.R
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivitySignUpBinding
import com.teampophory.pophory.feature.home.HomeActivity
import com.teampophory.pophory.feature.onboarding.OnBoardingActivity
import com.teampophory.pophory.feature.signup.adapter.SignUpViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySignUpBinding::inflate)
    //TODO 수정 필요
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //TODO 수정 필요
        binding.btnNext.isEnabled = true

//        val finalHost = NavHostFragment.create(R.navigation.nav_signup_graph)
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.nav_host_fragment, finalHost)
//            .setPrimaryNavigationFragment(finalHost) // this is the equivalent to app:defaultNavHost="true"
//            .commit()

        //다음 버튼
        setOnClickNextButton()
        //툴바 뒤로 가기 버튼
        setOnToolbarBackPressed()
        //기기 뒤로 가기 버튼
        //TODO 수정 필요
        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun setOnToolbarBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()

        binding.btnBack.setOnClickListener {
            val currentFragment = navController.currentDestination?.label
            when (currentFragment) {
                SIGN_UP_FIRST_FRAGMENT -> {
                    val intent = Intent(this, OnBoardingActivity::class.java)
                    startActivity(intent)
                }
                SIGN_UP_SECOND_FRAGMENT -> {
                    navController.navigate(R.id.action_signUpSecondFragment_to_signUpFirstFragment)
                }
                SIGN_UP_THIRD_FRAGMENT -> {
                    navController.navigate(R.id.action_signUpThirdFragment_to_signUpSecondFragment)
                }
            }
        }
    }

    private fun setOnClickNextButton() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()

        binding.btnNext.setOnClickListener {
            val currentFragment = navController.currentDestination?.label
            when (currentFragment) {
                SIGN_UP_FIRST_FRAGMENT -> {
                    navController.navigate(R.id.action_signUpFirstFragment_to_signUpSecondFragment)
                }
                SIGN_UP_SECOND_FRAGMENT -> {
                    navController.navigate(R.id.action_signUpSecondFragment_to_signUpThirdFragment)
                }
                SIGN_UP_THIRD_FRAGMENT -> {
                    val intent = Intent(this, StartPophoryActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    companion object {
        const val SIGN_UP_FIRST_FRAGMENT = "SignUpFirstFragment"
        const val SIGN_UP_SECOND_FRAGMENT = "SignUpSecondFragment"
        const val SIGN_UP_THIRD_FRAGMENT = "SignUpThirdFragment"
    }



//    private fun observeEvent() {
//        viewModel.signUpResult.observe(this) {
//            startActivity(StartPophoryActivity.getIntent(this))
//        }
//
//        viewModel.nicknameCheckResult.observe(this) {
////            if (!it.isDuplicated) {
////                val nextPosition = currentPosition + 1
////                binding.viewpager.currentItem = nextPosition
////            } else {
////                supportFragmentManager.commit(allowStateLoss = true) {
////                    add(SignUpDialogFragment(), SignUpDialogFragment::class.java.simpleName)
////                }
////            }
//        }
//        viewModel.buttonState.observe(this) {
//            binding.btnNext.isEnabled = it
//        }
//    }

//    private fun setOnBackPressed() {
//        binding.btnBack.setOnClickListener {
////            when (currentPosition) {
////                0 -> {
////                    val intent = Intent(this, OnBoardingActivity::class.java)
////                    startActivity(intent)
////                    finish()
////                }
////
////                1 -> {
////                    binding.viewpager.currentItem = 0
////                }
////
////                2 -> {
////                    binding.viewpager.currentItem = 1
////                }
////            }
//        }
//    }


//    private fun setOnClickNextButton() {
//        binding.btnNext.setOnClickListener {
//            it.findNavController().navigate(R.id.action_signUpFirstFragment_to_signUpSecondFragment)
//
////            when (currentPosition) {
////                0 -> binding.viewpager.currentItem = currentPosition + 1
////                1 -> viewModel.nicknameCheck()
////                2 -> viewModel.signUp()
////            }
//        }
//    }
//
//
//    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
//        override fun onPageScrolled(
//            position: Int,
//            positionOffset: Float,
//            positionOffsetPixels: Int
//        ) {
//            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
////            with(binding) {
////                tabFirst.isSelected = position == 0
////                tabSecond.isSelected = position == 1
////                tabThird.isSelected = position == 2
////            }
//
//        }
//
//        override fun onPageSelected(position: Int) {
//            super.onPageSelected(position)
//            currentPosition = position
//            binding.btnNext.text = if (position == 2) "완료하기" else "다음으로 넘어가기"
//            binding.btnNext.isEnabled = position == 2
//        }
//    }
//
//    private fun setViewPager() {
////        binding.viewpager.apply {
////            adapter = SignUpViewPagerAdapter(this@SignUpActivity)
////            isUserInputEnabled = false
////        }
////        binding.viewpager.registerOnPageChangeCallback(pageChangeCallback)
//    }
}