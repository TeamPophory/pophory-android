package com.teampophory.pophory.feature.home

import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.teampophory.pophory.R
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityHomeBinding
import com.teampophory.pophory.feature.HomeViewModel
import com.teampophory.pophory.feature.home.mypage.MyPageFragment
import com.teampophory.pophory.feature.home.photo.AddPhotoActivity
import com.teampophory.pophory.feature.home.store.StoreFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by viewBinding(ActivityHomeBinding::inflate)
    private val viewModel by viewModels<HomeViewModel>()
    private val imagePicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        it?.let { uri ->
            val currentAlbum = viewModel.currentAlbum
            currentAlbum?.let { album ->
                startActivity(AddPhotoActivity.getIntent(this, uri.toString(), album))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        binding.bottomNavFav.setOnClickListener {
            imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
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