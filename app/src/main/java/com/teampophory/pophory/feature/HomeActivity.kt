package com.teampophory.pophory.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.teampophory.pophory.R
import com.teampophory.pophory.databinding.ActivityHomeBinding
import com.teampophory.pophory.feature.my_page.MyPageFragment
import com.teampophory.pophory.feature.store.StoreFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigationBar()
        initializeDefaultFragment(savedInstanceState)
    }

    private fun setupBottomNavigationBar() {
        binding.homeBottomNav.setOnItemSelectedListener { item ->
            val selectedFragment = when (item.itemId) {
                R.id.menu_store -> StoreFragment()
                R.id.menu_my_page -> MyPageFragment()
                else -> null
            }
            selectedFragment?.let { changeFragment(it) }
            return@setOnItemSelectedListener selectedFragment != null
        }
    }

    private fun initializeDefaultFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val homeFragment = StoreFragment()
            changeFragment(homeFragment)
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.home_fcv, fragment)
        }
    }
}