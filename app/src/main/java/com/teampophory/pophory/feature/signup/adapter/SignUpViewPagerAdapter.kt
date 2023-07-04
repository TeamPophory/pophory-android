package com.teampophory.pophory.feature.signup.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.teampophory.pophory.feature.signup.SignUpActivity
import com.teampophory.pophory.feature.signup.SignUpButtonInterface
import com.teampophory.pophory.feature.signup.SignUpFirstFragment
import com.teampophory.pophory.feature.signup.SignUpSecondFragment
import com.teampophory.pophory.feature.signup.SignUpThirdFragment

class SignUpViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val buttonState: SignUpButtonInterface
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                SignUpFirstFragment().apply {
                    setSignUpButtonInterface(buttonState)
                }
            }

            1 -> {
                SignUpSecondFragment().apply {
                    setSignUpButtonInterface(buttonState)
                }
            }

            else -> {
                SignUpThirdFragment()
            }
        }

    }

}