package com.teampophory.pophory.feature.sign_up.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.teampophory.pophory.feature.sign_up.SignUpFirstFragment
import com.teampophory.pophory.feature.sign_up.SignUpSecondFragment
import com.teampophory.pophory.feature.sign_up.SignUpThirdFragment

class SignUpViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    private lateinit var viewPagerAdapter: SignUpViewPagerAdapter
    private val fragments = listOf(SignUpFirstFragment(), SignUpSecondFragment(), SignUpThirdFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}