package com.teampophory.pophory.feature.auth.signup

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.feature.auth.R
import com.teampophory.pophory.feature.auth.databinding.ActivitySignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySignUpBinding::inflate)

    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observeEvent()
        // 다음 버튼
        setOnClickNextButton()
        // 툴바 뒤로 가기 버튼
        setOnToolbarBackPressed()
    }

    private fun setOnToolbarBackPressed() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_sign_up_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()

        binding.btnBack.setOnClickListener {
            val currentFragment = navController.currentDestination?.label
            when (currentFragment) {
                SIGN_UP_FIRST_FRAGMENT -> finish()
                SIGN_UP_SECOND_FRAGMENT -> navController.popBackStack()
                SIGN_UP_THIRD_FRAGMENT -> navController.popBackStack()
            }
        }
    }

    private fun setOnClickNextButton() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_sign_up_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()

        binding.btnNext.setOnClickListener {
            when (navController.currentDestination?.label) {
                SIGN_UP_FIRST_FRAGMENT -> navigateToFragment(1)
                SIGN_UP_SECOND_FRAGMENT -> viewModel.nicknameCheck()
                SIGN_UP_THIRD_FRAGMENT -> viewModel.signUp()
            }
        }
        binding.btnNext.text = when (navController.currentDestination?.label) {
            SIGN_UP_FIRST_FRAGMENT, SIGN_UP_SECOND_FRAGMENT -> "다음으로 넘어가기"
            else -> "완료하기"
        }
    }

    private fun navigateToFragment(start: Int) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_sign_up_fragment) as NavHostFragment
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
            if (!it) {
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
