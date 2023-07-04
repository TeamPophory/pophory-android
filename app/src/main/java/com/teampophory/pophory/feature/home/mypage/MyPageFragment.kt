package com.teampophory.pophory.feature.home.mypage

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.teampophory.pophory.R
import com.teampophory.pophory.common.fragment.toast
import com.teampophory.pophory.common.view.GridSpacingItemDecoration
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.FragmentMypageBinding
import com.teampophory.pophory.feature.home.mypage.adapter.MyPageAdapter

class MyPageFragment : Fragment() {
    private val binding by viewBinding(FragmentMypageBinding::bind)

    private var myPageAdapter: MyPageAdapter? = null

    private val viewModel by viewModels<MyPageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        setOnClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        myPageAdapter = null
    }

    private fun initObserver() {
        viewModel.photoList.observe(viewLifecycleOwner) { myPageInfoState ->
            when (myPageInfoState) {
                is MyPageInfoState.Uninitialized -> {
                    viewModel.getMyPageInfo()
                    initRecyclerView()
                }

                is MyPageInfoState.Loading -> {}

                is MyPageInfoState.SuccessMyPageInfo -> {
                    with(binding) {
                        tvMypageName.text = myPageInfoState.data.realName
                        tvMypageToolbarNickname.text = "@".plus(myPageInfoState.data.nickname)
                        tvMypagePictureCount.text = getString(
                            R.string.mypage_picture_count,
                            myPageInfoState.data.photoCount
                        )
                        setSpannableString(myPageInfoState.data.photoCount)

                        val isNotEmpty = myPageInfoState.data.photos.isNotEmpty()

                        if (isNotEmpty) {
                            myPageAdapter?.submitList(myPageInfoState.data.photos)
                        }
                        rvMypage.isVisible = isNotEmpty
                        ivMypageFeedEmpty.isVisible = !isNotEmpty
                        tvMypageFeedEmpty.isVisible = !isNotEmpty
                    }
                }

                is MyPageInfoState.Error -> {}
            }
        }
    }

    private fun initRecyclerView() {
        val gridLayoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)


        myPageAdapter = MyPageAdapter { position ->
            val photoList = viewModel.photoList.value
            if (photoList is MyPageInfoState.SuccessMyPageInfo) {
                val itemId = photoList.data.photos.getOrNull(position)?.photoId
                toast(itemId.toString());
                //TODO intent photo_detail activity
            }
        }

        binding.rvMypage.apply {
            layoutManager = gridLayoutManager
            adapter = myPageAdapter
            isNestedScrollingEnabled = false
        }.addItemDecoration(GridSpacingItemDecoration(3, 10, false))
    }

    private fun setSpannableString(myPageInfoDataPhotoCount: Int) {
        val fullText = getString(R.string.mypage_picture_count, myPageInfoDataPhotoCount)
        val coloredText = myPageInfoDataPhotoCount.toString()

        val spannableStringBuilder = SpannableStringBuilder(fullText)
        val start = fullText.indexOf(coloredText)
        val end = start + coloredText.length

        if (start != -1) {
            spannableStringBuilder.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.pophory_purple
                    )
                ),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        binding.tvMypagePictureCount.text = spannableStringBuilder
    }

    private fun setOnClickListener() {
        binding.ivToolbarSetting.setOnClickListener {
            //TODO intent to setting
        }
    }
}
