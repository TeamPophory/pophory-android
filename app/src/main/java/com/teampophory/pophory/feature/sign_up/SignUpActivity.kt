package com.teampophory.pophory.feature.sign_up

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.teampophory.pophory.databinding.ActivitySignUpBinding
import com.teampophory.pophory.feature.sign_up.adapter.SignUpViewPagerAdapter

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignUpBinding
    private lateinit var viewPager : ViewPager2
    private lateinit var adapter : SignUpViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViewPager()
    }

    private fun setViewPager() {
        adapter = SignUpViewPagerAdapter(this)
        viewPager = binding.viewpager

        viewPager.adapter = adapter
    }
}