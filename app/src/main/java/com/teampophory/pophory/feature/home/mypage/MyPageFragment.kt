package com.teampophory.pophory.feature.home.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.teampophory.pophory.R
import com.teampophory.pophory.common.fragment.hideLoading
import com.teampophory.pophory.common.fragment.showLoading
import com.teampophory.pophory.common.view.dp
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.FragmentMypageBinding
import com.teampophory.pophory.feature.album.detail.AlbumDetailActivity
import com.teampophory.pophory.feature.home.mypage.adapter.MyPageAdapter
import com.teampophory.pophory.feature.home.mypage.adapter.MyPageAdapter.Companion.VIEW_TYPE_EMPTY
import com.teampophory.pophory.feature.home.mypage.adapter.MyPageAdapter.Companion.VIEW_TYPE_PHOTO
import com.teampophory.pophory.feature.home.mypage.adapter.MyPageAdapter.Companion.VIEW_TYPE_PROFILE
import com.teampophory.pophory.feature.home.mypage.util.GridSpacingCustomDecoration
import com.teampophory.pophory.feature.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

                is MyPageInfoState.Loading -> {
                    showLoading()
                }

                is MyPageInfoState.SuccessMyPageInfo -> {
                    hideLoading()
                    val photoItems =
                        myPageInfoState.data.filterIsInstance<MyPageDisplayItem.Photo>()
                    val isEmpty = photoItems.isEmpty()

                    val myPageInfoData = if (isEmpty) {
                        myPageInfoState.data.toMutableList()
                            .also { it.add(MyPageDisplayItem.Empty) }
                    } else {
                        myPageInfoState.data
                    }

                    with(binding) {
                        val profileItem =
                            myPageInfoState.data.firstOrNull { it is MyPageDisplayItem.Profile } as? MyPageDisplayItem.Profile
                        tvMypageToolbarNickname.text = "@${profileItem?.nickname}"
                        myPageAdapter?.submitList(myPageInfoData)
                    }
                }

                is MyPageInfoState.Error -> {
                    hideLoading()
                }

                else -> {}
            }
        }
    }

    private fun initRecyclerView() {
        val gridLayoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)

        myPageAdapter = MyPageAdapter { photos ->
            val photoList = viewModel.myPageInfo.value
            if (photoList is MyPageInfoState.SuccessMyPageInfo) {
                AlbumDetailActivity.newIntent(requireContext(), photos.photo).let(::startActivity)
            }
        }

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (myPageAdapter?.getItemViewType(position)) {
                    VIEW_TYPE_PROFILE -> 3
                    VIEW_TYPE_PHOTO -> 1
                    VIEW_TYPE_EMPTY -> 3
                    else -> 1
                }
            }
        }

        binding.rvMypage.apply {
            layoutManager = gridLayoutManager
            adapter = myPageAdapter
            isNestedScrollingEnabled = false
        }.addItemDecoration(GridSpacingCustomDecoration(3, 2.dp, false))
    }

    private fun setOnClickListener() {
        binding.ivToolbarSetting.setOnClickListener {
            startActivity(Intent(requireContext(), SettingActivity::class.java))
        }
    }

    companion object {
        fun newInstance() = MyPageFragment()
    }
}
