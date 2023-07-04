package com.teampophory.pophory.feature.home.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.teampophory.pophory.feature.home.mypage.adapter.MyPageAdapter.Companion.VIEW_TYPE_PHOTO
import com.teampophory.pophory.feature.home.mypage.adapter.MyPageAdapter.Companion.VIEW_TYPE_PROFILE

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
        viewModel.myPageInfo.observe(viewLifecycleOwner) { myPageInfoState ->
            when (myPageInfoState) {
                is MyPageInfoState.Uninitialized -> {
                    viewModel.getMyPageInfo()
                    initRecyclerView()
                }

                is MyPageInfoState.Loading -> {}

                is MyPageInfoState.SuccessMyPageInfo -> {
                    val isNotEmpty = myPageInfoState.data.photos.isNotEmpty()
                    val myPageInfoData = arrayListOf<Any>().apply {
                        add(myPageInfoState.data)
                        addAll(myPageInfoState.data.photos)
                    }

                    with(binding) {
                        tvMypageToolbarNickname.text = "@${myPageInfoState.data.nickname}"
                        if (isNotEmpty) {
                            myPageAdapter?.submitList(myPageInfoData)
                        }
                        ivMypageFeedEmpty.isVisible = !isNotEmpty
                        tvMypageFeedEmpty.isVisible = !isNotEmpty
                        rvMypage.isVisible = isNotEmpty
                    }
                }

                is MyPageInfoState.Error -> {}
            }
        }
    }

    private fun initRecyclerView() {
        val gridLayoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)

        myPageAdapter = MyPageAdapter { photo ->
            val photoList = viewModel.myPageInfo.value
            if (photoList is MyPageInfoState.SuccessMyPageInfo) {
                toast(photo.photoId.toString());
                //TODO intent photo_detail activity
            }
        }

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    VIEW_TYPE_PROFILE -> 3
                    VIEW_TYPE_PHOTO -> 1
                    else -> 1
                }
            }
        }

        binding.rvMypage.apply {
            layoutManager = gridLayoutManager
            adapter = myPageAdapter
            isNestedScrollingEnabled = false
        }.addItemDecoration(GridSpacingItemDecoration(3, 2, false))
    }

    private fun setOnClickListener() {
        binding.ivToolbarSetting.setOnClickListener {
            //TODO intent to setting
        }
    }


}
