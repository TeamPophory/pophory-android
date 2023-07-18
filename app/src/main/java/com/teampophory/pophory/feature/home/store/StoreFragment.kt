package com.teampophory.pophory.feature.home.store

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import com.teampophory.pophory.R
import com.teampophory.pophory.common.fragment.colorOf
import com.teampophory.pophory.common.fragment.hideLoading
import com.teampophory.pophory.common.fragment.showLoading
import com.teampophory.pophory.common.fragment.stringOf
import com.teampophory.pophory.common.fragment.toast
import com.teampophory.pophory.common.fragment.viewLifeCycle
import com.teampophory.pophory.common.fragment.viewLifeCycleScope
import com.teampophory.pophory.common.primitive.textAppearance
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.FragmentStoreBinding
import com.teampophory.pophory.feature.HomeViewModel
import com.teampophory.pophory.feature.album.list.AlbumListActivity
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
                    setupViewPager()
                    setOnClickListener()
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
        homeViewModel.albumCountUpdate.flowWithLifecycle(viewLifeCycle).onEach {
            viewModel.getAlbums()
        }.launchIn(viewLifeCycleScope)
    }

    private fun setOnClickListener() {
        with(binding) {
            ivStoreEdit.setOnClickListener {
                //TODO intent to eidt
            }
            //seekBar 터치 비활성화
            seekBarStore.setOnTouchListener { _, _ -> true }
        }
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
                androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    homeViewModel.onUpdateAlbumPosition(position)
                    val photoCount =
                        storeAdapter?.currentList?.getOrNull(position)?.photoCount.toString()
                    val photoLimit =
                        storeAdapter?.currentList?.getOrNull(position)?.photoLimit.toString()
                    val maxPhoto = 15

                    //사진 갯수 텍스트 색상변경
                    setSpannableCountString(photoCount,photoLimit)

                    //seekBar 게이지 설정
                    binding.seekBarStore.progress =
                        ((photoCount.toDouble() / maxPhoto) * 100).toInt()

                    //앨범을 가득 채웠을 때 thumb 변경
                    if (photoCount.toInt() == maxPhoto) {
                        binding.seekBarStore.thumb = context.getDrawable(R.drawable.ic_thumb_full)
                    }
                }
            })
        }
    }

    private fun updatePhotoCount() {
        val position = homeViewModel.currentAlbumPosition.value
        val photoCount =
            homeViewModel.currentAlbums.value?.getOrNull(position)?.photoCount.toString()
        val photoLimit =
            homeViewModel.currentAlbums.value?.getOrNull(position)?.photoLimit.toString()
        setSpannableString(
            "$photoCount/$photoLimit",
            photoCount,
            binding.tvStoreAlbumPhotoCount,
            R.color.pophory_purple,
            R.style.TextAppearance_Pophory_HeadLineMedium
        )
    }

    private fun setSpannableWelcomeString() {
        val fullText = getString(R.string.store_welcome)
        val coloredText = POPHORY_STORE_TEXT
        setSpannableString(
            fullText,
            coloredText,
            binding.tvStoreWelcome,
            R.color.pophory_purple,
            R.style.TextAppearance_Pophory_HeadLineBold
        )
    }

    private fun setSpannableCountString(photoCount: String,photoLimit: String) {
        val fullText = "$photoCount/$photoLimit"
        val coloredText = photoCount
        setSpannableString(
            fullText,
            coloredText,
            binding.tvStoreAlbumPhotoCount,
            R.color.pophory_purple,
            R.style.TextAppearance_Pophory_HeadLineMedium
        )
    }

    private fun setSpannableString(fullText: String, coloredText: String, textView: TextView, color: Int, style: Int) {
        val splitText = fullText.split(coloredText)
        buildSpannedString {
            color(colorOf(color)) {
                textAppearance(requireContext(), style) {
                    append(coloredText)
                }
            }
            append(splitText.getOrNull(1).orEmpty())

            //15일경우 예외상황 처리
            if(coloredText.equals("15")) {
                append("15")
            }
        }.let {
            textView.text = it
        }
    }

    companion object {
        const val POPHORY_STORE_TEXT = "포포리 앨범"
        fun newInstance() = StoreFragment()
    }
}
