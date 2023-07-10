package com.teampophory.pophory.feature.home.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.teampophory.pophory.R
import com.teampophory.pophory.common.fragment.colorOf
import com.teampophory.pophory.common.fragment.hideLoading
import com.teampophory.pophory.common.fragment.showLoading
import com.teampophory.pophory.common.primitive.textAppearance
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.FragmentStoreBinding
import com.teampophory.pophory.feature.HomeViewModel
import com.teampophory.pophory.feature.album.list.AlbumListActivity
import com.teampophory.pophory.feature.home.store.apdater.StoreAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreFragment : Fragment() {
    private val binding by viewBinding(FragmentStoreBinding::bind)

    private var storeAdapter: StoreAdapter? = null

    private val viewModel by viewModels<StoreViewModel>()
    private val homeViewModel by activityViewModels<HomeViewModel>()

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
        setSpannableString()
    }

    private fun initStoreObserver() {
        viewModel.albums.observe(viewLifecycleOwner) { storeState ->
            when (storeState) {
                is StoreState.Uninitialized -> {
                    viewModel.getAlbums()
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


    private fun initHomeObserver() {
        homeViewModel.albumCountUpdate.observe(viewLifecycleOwner) {
            viewModel.getAlbums()
        }
    }

    private fun setupViewPager() {
        storeAdapter = StoreAdapter { albumItem ->
            AlbumListActivity.newInstance(requireContext(), albumItem).let(::startActivity)
        }
        binding.viewpagerStore.run {
            adapter = storeAdapter
            isUserInputEnabled = false
            registerOnPageChangeCallback(object :
                androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    homeViewModel.onUpdateAlbumPosition(position)
                    val photoCount = storeAdapter?.currentList?.get(position)?.photoCount.toString()
                    binding.tvStoreAlbumPhotoCount.text = "$photoCount/15"
                }
            })
        }
    }

    private fun updatePhotoCount() {
        val position = homeViewModel.currentAlbumPosition.value
        val photoCount =
            homeViewModel.currentAlbum.value?.getOrNull(position)?.photoCount.toString()
        binding.tvStoreAlbumPhotoCount.text = "$photoCount/15"
    }

    private fun setSpannableString() {
        val fullText = getString(R.string.store_welcome)
        val coloredText = "포포리 앨범" // 색상을 변경하려는 특정 단어
        val splittedText = fullText.split(coloredText)

        val text = buildSpannedString {
            color(colorOf(R.color.pophory_purple)) {
                textAppearance(requireContext(), R.style.TextAppearance_Pophory_HeadLineBold) {
                    append(coloredText)
                }
            }
            append(splittedText[1])
        }

        binding.tvStoreWelcome.text = text
    }

    companion object {
        fun newInstance() = StoreFragment()
    }
}