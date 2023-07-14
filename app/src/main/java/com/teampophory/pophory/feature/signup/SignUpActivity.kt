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

    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnNext.isEnabled = true

        observeEvent()
        //다음 버튼
        setOnClickNextButton()
        //툴바 뒤로 가기 버튼
        setOnToolbarBackPressed()
    }

    private fun setOnToolbarBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()

        binding.btnBack.setOnClickListener {
            val currentFragment = navController.currentDestination?.label
            when (currentFragment) {
                SIGN_UP_FIRST_FRAGMENT -> startActivity(Intent(this, OnBoardingActivity::class.java))
                SIGN_UP_SECOND_FRAGMENT -> navController.popBackStack()
                SIGN_UP_THIRD_FRAGMENT -> navController.popBackStack()
            }
        }
    }

    private fun setOnClickNextButton() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()

        binding.btnNext.setOnClickListener {
            val currentFragment = navController.currentDestination?.label
            when (currentFragment) {
                SIGN_UP_FIRST_FRAGMENT -> navigateToFragment(1)
                SIGN_UP_SECOND_FRAGMENT -> viewModel.nicknameCheck()
                SIGN_UP_THIRD_FRAGMENT -> viewModel.signUp()
            }
        }
    }

    private fun navigateToFragment(start: Int) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()

        when (start) {
            1 -> navController.navigate(R.id.action_signUpFirstFragment_to_signUpSecondFragment)
            2 -> navController.navigate(R.id.action_signUpSecondFragment_to_signUpThirdFragment)
        }
    }

    private fun observeEvent() {

        viewModel.signUpResult.observe(this) {
            startActivity(StartPophoryActivity.getIntent(this))
        }

        viewModel.nicknameCheckResult.observe(this) {
            if (!it.isDuplicated) {
                navigateToFragment(2)
            } else {
                supportFragmentManager.commit(allowStateLoss = true) {
                    add(SignUpDialogFragment(), SignUpDialogFragment::class.java.simpleName)
                }
            }
        }
        viewModel.buttonState.observe(this) {
            binding.btnNext.isEnabled = it
        }
    }
    companion object {
        const val SIGN_UP_FIRST_FRAGMENT = "SignUpFirstFragment"
        const val SIGN_UP_SECOND_FRAGMENT = "SignUpSecondFragment"
        const val SIGN_UP_THIRD_FRAGMENT = "SignUpThirdFragment"
    }
}