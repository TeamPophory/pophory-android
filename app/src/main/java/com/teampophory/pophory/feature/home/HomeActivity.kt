package com.teampophory.pophory.feature.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.teampophory.pophory.R
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityHomeBinding
import com.teampophory.pophory.feature.HomeViewModel
import com.teampophory.pophory.feature.home.mypage.MyPageFragment
import com.teampophory.pophory.feature.home.photo.AddPhotoActivity
import com.teampophory.pophory.feature.home.store.StoreFragment
import com.teampophory.pophory.feature.home.store.model.AlbumItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by viewBinding(ActivityHomeBinding::inflate)
    private val viewModel by viewModels<HomeViewModel>()

    private val fragmentMap = mapOf(
        R.id.menu_store to StoreFragment(),
        R.id.menu_my_page to MyPageFragment()
    )


    private val addPhotoResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val albumItem = it.data?.getParcelableExtra(AddPhotoActivity.EXTRA_ALBUM_ITEM) as? AlbumItem
            lifecycleScope.launch {
                viewModel.onUpdateAlbum(albumItem)
            }
        }
    }

    private val imagePicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        val currentAlbum = viewModel.currentAlbum.value
        val intent = currentAlbum?.let { albumItem ->
            AddPhotoActivity.getIntent(this, uri.toString(), albumItem)
        }
        if (uri != null) {
            addPhotoResultLauncher.launch(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNavigationBar()

        //init fragment setting
        initializeDefaultFragment(savedInstanceState)
        binding.homeBottomNav.selectedItemId = R.id.menu_store
    }

    private fun setupBottomNavigationBar() {

        binding.homeBottomNav.setOnItemSelectedListener { item ->
            val selectedFragment = fragmentMap[item.itemId]
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
            supportFragmentManager.fragments.forEach { hide(it) } // 모든 Fragment를 숨깁니다.
            if (!fragment.isAdded) {
                add(R.id.home_fcv, fragment)
            } else {
                show(fragment) // 선택한 Fragment만 보여줍니다.
            }
        }
    }


    companion object {
        @JvmStatic
        fun getIntent(context: Context) = Intent(context, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    }
}