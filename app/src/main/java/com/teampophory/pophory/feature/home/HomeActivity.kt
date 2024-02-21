package com.teampophory.pophory.feature.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.teampophory.pophory.R
import com.teampophory.pophory.common.context.stringOf
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityHomeBinding
import com.teampophory.pophory.feature.home.add.AddPhotoBottomSheet
import com.teampophory.pophory.feature.home.mypage.MyPageFragment
import com.teampophory.pophory.feature.home.store.StoreFragment
import com.teampophory.pophory.designsystem.dialog.DialogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by viewBinding(ActivityHomeBinding::inflate)
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNavigationBar()
        initializeDefaultFragment(savedInstanceState)
    }

    private fun setupBottomNavigationBar() {
        binding.homeBottomNav.setOnItemSelectedListener { item ->
            val selectedFragment = when (item.itemId) {
                R.id.menu_store -> StoreFragment.newInstance()
                R.id.menu_my_page -> MyPageFragment.newInstance()
                else -> null
            }
            selectedFragment?.let { changeFragment(it) }
            return@setOnItemSelectedListener selectedFragment != null
        }

        binding.homeBottomNav.setOnItemReselectedListener { }

        binding.bottomNavFav.setOnClickListener {
            val currentAlbumPosition = viewModel.homeState.value.currentAlbumPosition
            val albumItem = viewModel.homeState.value.currentAlbums?.getOrNull(currentAlbumPosition)
            if (albumItem != null) {
                if (albumItem.photoLimit <= albumItem.photoCount) {
                    DialogUtil.showOneButtonDialog(
                        supportFragmentManager = supportFragmentManager,
                        title = stringOf(R.string.dialog_title_photo_limit),
                        description = stringOf(R.string.dialog_message_photo_limit),
                        buttonText = stringOf(R.string.ok),
                        imageResId = R.drawable.ic_customizing_done,
                    )
                    return@setOnClickListener
                }
            }
            val modalBottomSheet = AddPhotoBottomSheet()
            modalBottomSheet.show(supportFragmentManager, AddPhotoBottomSheet.TAG)
        }
    }

    private fun initializeDefaultFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val homeFragment = StoreFragment.newInstance()
            changeFragment(homeFragment)
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.home_fcv, fragment)
        }
    }

    companion object {
        @JvmStatic
        fun getIntent(context: Context) = Intent(context, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    }
}
