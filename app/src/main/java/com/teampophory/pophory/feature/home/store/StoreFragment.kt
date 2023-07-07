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
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.teampophory.pophory.R
import com.teampophory.pophory.common.fragment.colorOf
import com.teampophory.pophory.common.fragment.hideLoading
import com.teampophory.pophory.common.fragment.showLoading
import com.teampophory.pophory.common.primitive.textAppearance
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.FragmentStoreBinding
import com.teampophory.pophory.feature.HomeViewModel
import com.teampophory.pophory.feature.album.list.AlbumListActivity
import com.teampophory.pophory.feature.home.store.apdater.OnPageChangedListener
import com.teampophory.pophory.feature.home.store.apdater.StoreAdapter
import com.teampophory.pophory.feature.home.store.model.AlbumItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StoreFragment : Fragment(), OnPageChangedListener {
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
        initObserver()
        setSpannableString()
    }

    private fun initObserver() {
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
                    storeAdapter?.submitList(storeState.data)

                    //최초 데이터 세팅
                    storeState.data.firstOrNull()?.let { onPageChanged(it) }
                }

                is StoreState.Error -> {
                    hideLoading()
                }

                else -> {}
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.currentAlbum.collect {
                viewModel.getAlbums()
            }
        }
    }

    private fun setupViewPager() {
        storeAdapter = StoreAdapter({ albumItem ->
            AlbumListActivity.newInstance(requireContext(), albumItem.id).let(::startActivity)
        }, this)

        binding.viewpagerStore.adapter = storeAdapter

        //1차 스프린트용 입력 방지
        binding.viewpagerStore.isUserInputEnabled = false

        // 페이지가 변경될 때마다 콜백 등록
        binding.viewpagerStore.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val currentItem = storeAdapter?.currentList?.get(position)
                currentItem?.let { onPageChanged(it) }
            }
        })
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

    override fun onPageChanged(albumItem: AlbumItem) {
        homeViewModel.onUpdateAlbum(albumItem)
        binding.tvStoreAlbumPhotoCount.text = albumItem.photoCount.toString() + "/30"
    }
}