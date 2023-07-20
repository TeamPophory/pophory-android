package com.teampophory.pophory.feature.home.store

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.teampophory.pophory.R
import com.teampophory.pophory.common.fragment.colorOf
import com.teampophory.pophory.common.fragment.hideLoading
import com.teampophory.pophory.common.fragment.showLoading
import com.teampophory.pophory.common.fragment.viewLifeCycle
import com.teampophory.pophory.common.fragment.viewLifeCycleScope
import com.teampophory.pophory.common.primitive.textAppearance
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.FragmentStoreBinding
import com.teampophory.pophory.feature.album.cover.AlbumCoverEditActivity
import com.teampophory.pophory.feature.album.list.AlbumListActivity
import com.teampophory.pophory.feature.home.HomeViewModel
import com.teampophory.pophory.feature.home.store.apdater.StoreAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class StoreFragment : Fragment() {
    private val binding by viewBinding(FragmentStoreBinding::bind)
    private var storeAdapter: StoreAdapter? = null
    private val viewModel by viewModels<StoreViewModel>()
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val albumListAddPhotoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                viewModel.getAlbums()
            }
        }
    private val albumCoverChangeLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                viewModel.getAlbums()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_store, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStoreObserver()
        initHomeObserver()
        setSpannableWelcomeString()
    }

    private fun initStoreObserver() {
        viewModel.albums.observe(viewLifecycleOwner) { storeState ->
            when (storeState) {
                is StoreState.Uninitialized -> {
                    viewModel.getAlbums()
                    intiViews()
                    setupViewPager()
                }

                is StoreState.Loading -> {
                    showLoading()
                }

                is StoreState.SuccessAlbums -> {
                    hideLoading()
                    homeViewModel.onUpdateAlbum(storeState.data)
                    storeAdapter?.submitList(storeState.data)
                    updatePhotoCount()
                }

                is StoreState.Error -> {
                    hideLoading()
                }

                else -> {
                    hideLoading()
                }
            }
        }
    }

    private fun intiViews() {
        binding.ivEditButton.setOnClickListener {
            if (viewModel.albums.value is StoreState.SuccessAlbums) {
                val albumItems = (viewModel.albums.value as StoreState.SuccessAlbums).data
                val albumItem = albumItems.getOrNull(homeViewModel.currentAlbumPosition.value)
                val currentAlbumCoverId = albumItem?.albumCover ?: 1
                val intent = AlbumCoverEditActivity.newIntent(
                    context = requireContext(),
                    albumCoverId = currentAlbumCoverId,
                    albumId = albumItem?.id ?: 0
                )
                albumCoverChangeLauncher.launch(intent)
            }
        }
        binding.seekBarStore.setOnTouchListener { _, _ -> true }
    }

    private fun initHomeObserver() {
        homeViewModel.albumCountUpdate.flowWithLifecycle(viewLifeCycle).onEach {
            viewModel.getAlbums()
        }.launchIn(viewLifeCycleScope)
    }

    private fun setupViewPager() {
        storeAdapter = StoreAdapter { albumItem ->
            val intent = AlbumListActivity.newInstance(requireContext(), albumItem)
            albumListAddPhotoLauncher.launch(intent)
        }

        binding.viewpagerStore.run {
            adapter = storeAdapter
            isUserInputEnabled = false
            registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    homeViewModel.onUpdateAlbumPosition(position)
                    val photoCount =
                        storeAdapter?.currentList?.getOrNull(position)?.photoCount ?: 0
                    val photoLimit =
                        storeAdapter?.currentList?.getOrNull(position)?.photoLimit ?: 15

                    //사진 갯수 텍스트 색상변경
                    setSpannableCountString(photoCount.toString(), photoLimit.toString())

                    updateSeekBar(photoCount = photoCount, photoLimit = photoLimit)
                }
            })
        }
    }

    private fun updatePhotoCount() {
        val position = homeViewModel.currentAlbumPosition.value
        val photoCount =
            homeViewModel.currentAlbums.value?.getOrNull(position)?.photoCount ?: 0
        val photoLimit =
            homeViewModel.currentAlbums.value?.getOrNull(position)?.photoLimit ?: 15
        setSpannableString(
            "/$photoLimit",
            photoCount.toString(),
            binding.tvStoreAlbumPhotoCount,
            R.color.pophory_purple,
            R.style.TextAppearance_Pophory_HeadLineMedium
        )

        updateSeekBar(photoCount = photoCount, photoLimit = photoLimit)
    }

    private fun updateSeekBar(photoCount: Int, photoLimit: Int) {
        binding.seekBarStore.progress = (photoCount.toDouble() / photoLimit * 100).toInt()

        if (photoCount == photoLimit) {
            binding.seekBarStore.thumb =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_thumb_full)
        }
    }

    private fun setSpannableWelcomeString() {
        val notColoredText = getString(R.string.store_welcome)
        val coloredText = POPHORY_STORE_TEXT
        setSpannableString(
            notColoredText,
            coloredText,
            binding.tvStoreWelcome,
            R.color.pophory_purple,
            R.style.TextAppearance_Pophory_HeadLineBold
        )
    }

    private fun setSpannableCountString(photoCount: String, photoLimit: String) {
        val notColoredText = "/$photoLimit"
        setSpannableString(
            notColoredText,
            photoCount,
            binding.tvStoreAlbumPhotoCount,
            R.color.pophory_purple,
            R.style.TextAppearance_Pophory_HeadLineMedium
        )
    }

    private fun setSpannableString(
        notColoredText: String,
        coloredText: String,
        textView: TextView,
        color: Int,
        style: Int
    ) {
        buildSpannedString {
            color(colorOf(color)) {
                textAppearance(requireContext(), style) {
                    append(coloredText)
                }
            }
            append(notColoredText)
        }.let {
            textView.text = it
        }
    }

    companion object {
        const val POPHORY_STORE_TEXT = "포포리 앨범"
        fun newInstance() = StoreFragment()
    }
}
