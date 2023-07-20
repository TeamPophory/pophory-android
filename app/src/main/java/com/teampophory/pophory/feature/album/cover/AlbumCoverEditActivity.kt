package com.teampophory.pophory.feature.album.cover

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.teampophory.pophory.common.activity.hideLoading
import com.teampophory.pophory.common.activity.showError
import com.teampophory.pophory.common.activity.showLoading
import com.teampophory.pophory.common.intent.intExtra
import com.teampophory.pophory.common.intent.longExtra
import com.teampophory.pophory.common.view.dp
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityAlbumCoverEditBinding
import com.teampophory.pophory.feature.album.cover.adapter.AlbumCoverEditAdapter
import com.teampophory.pophory.feature.album.cover.model.AlbumCoverItem
import com.teampophory.pophory.util.HorizontalMarginItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AlbumCoverEditActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityAlbumCoverEditBinding::inflate)
    private val viewModel by viewModels<AlbumCoverEditViewModel>()
    private val currentAlbumCoverId by intExtra(1)
    private val albumId by longExtra(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initObserve()
    }

    private fun initObserve() {
        viewModel.albumEditState.flowWithLifecycle(lifecycle).onEach {
            when (it) {
                is AlbumEditState.Uninitialized -> {
                    initViews()
                }

                is AlbumEditState.Loading -> {
                    showLoading()
                }

                is AlbumEditState.Success -> {
                    hideLoading()
                    setResult(RESULT_OK)
                    finish()
                }

                is AlbumEditState.Error -> {
                    hideLoading()
                    showError()
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun initViews() {
        initEditButton()
        initAlbumCoverViewPager()
        initSelectorClickListener()
        setAlbumCoverData()
    }

    private fun initEditButton() {
        binding.tvEditButton.setOnClickListener {
            viewModel.patchAlbumCover(albumId)
        }
    }

    private fun initAlbumCoverViewPager() {
        val nextItemVisibleDp = 70.dp
        val currentItemHorizontalMarginDp = 29.dp
        val pageTranslationX = nextItemVisibleDp + currentItemHorizontalMarginDp
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
        }

        val itemDecoration = HorizontalMarginItemDecoration(60.dp)

        with(binding) {
            vpAlbumCover.apply {
                adapter = AlbumCoverEditAdapter()
                setPageTransformer(pageTransformer)
                addItemDecoration(itemDecoration)
                offscreenPageLimit = 1
                registerOnPageChangeCallback(onPageChangeCallback())
            }
        }

        (binding.vpAlbumCover.adapter as? AlbumCoverEditAdapter)?.submitList(viewModel.getAlbumItemList())
    }

    private fun onPageChangeCallback() = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            viewModel.updateCurrentPosition(position.toLong())
            val currentAlbumItem = viewModel.getAlbumItemList().getOrNull(position)
            when (currentAlbumItem?.theme) {
                AlbumCoverItem.AlbumTheme.FRIEND -> {
                    setSelectorState(AlbumCoverItem.AlbumTheme.FRIEND)
                }

                AlbumCoverItem.AlbumTheme.LOVE -> {
                    setSelectorState(AlbumCoverItem.AlbumTheme.LOVE)
                }

                AlbumCoverItem.AlbumTheme.MY_ALBUM -> {
                    setSelectorState(AlbumCoverItem.AlbumTheme.MY_ALBUM)
                }

                AlbumCoverItem.AlbumTheme.FAMILY -> {
                    setSelectorState(AlbumCoverItem.AlbumTheme.FAMILY)
                }

                else -> {
                    setSelectorState(AlbumCoverItem.AlbumTheme.FRIEND)
                }
            }
        }
    }

    private fun initSelectorClickListener() {
        with(binding) {
            ivAlbumFriend.setOnClickListener {
                setSelectorState(AlbumCoverItem.AlbumTheme.FRIEND)
                setCurrentAlumItem(0)
            }
            ivAlbumLove.setOnClickListener {
                setSelectorState(AlbumCoverItem.AlbumTheme.LOVE)
                setCurrentAlumItem(2)
            }
            ivAlbumMyAlbum.setOnClickListener {
                setSelectorState(AlbumCoverItem.AlbumTheme.MY_ALBUM)
                setCurrentAlumItem(4)
            }
            ivAlbumCollectBook.setOnClickListener {
                setSelectorState(AlbumCoverItem.AlbumTheme.FAMILY)
                setCurrentAlumItem(6)
            }
        }
    }

    private fun setAlbumCoverData() {
        val albumPosition = currentAlbumCoverId - 1
        setCurrentAlumItem(albumPosition)
        val currentAlbumTheme = viewModel.getAlbumItemList().getOrNull(albumPosition)?.theme
        if (currentAlbumTheme != null) {
            setSelectorState(currentAlbumTheme)
        }
    }

    private fun setCurrentAlumItem(albumId: Int) {
        binding.vpAlbumCover.setCurrentItem(albumId, true)
    }

    private fun setSelectorState(state: AlbumCoverItem.AlbumTheme) {
        val noneState = state == AlbumCoverItem.AlbumTheme.NONE
        with(binding) {
            ivAlbumSelectFriend.isVisible = state == AlbumCoverItem.AlbumTheme.FRIEND || noneState
            ivAlbumSelectLove.isVisible = state == AlbumCoverItem.AlbumTheme.LOVE
            ivAlbumSelectMyAlbum.isVisible = state == AlbumCoverItem.AlbumTheme.MY_ALBUM
            ivAlbumSelectCollectBook.isVisible = state == AlbumCoverItem.AlbumTheme.FAMILY
        }
    }

    companion object {
        fun newIntent(
            context: Context,
            albumCoverId: Int,
            albumId: Long
        ): Intent {
            return Intent(context, AlbumCoverEditActivity::class.java).apply {
                putExtra("currentAlbumCoverId", albumCoverId)
                putExtra("albumId", albumId)
            }
        }
    }
}