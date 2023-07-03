package com.teampophory.pophory.feature.home.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.teampophory.pophory.R
import com.teampophory.pophory.common.fragment.toast
import com.teampophory.pophory.common.view.ItemDiffCallback
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.FragmentMyPageBinding
import com.teampophory.pophory.feature.home.mypage.adapter.MyPageAdapter

class MyPageFragment : Fragment() {
    private val binding by viewBinding(FragmentMyPageBinding::bind)

    private var myPageAdapter: MyPageAdapter? = null

    private val viewModel by viewModels<MyPageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initRecyclerView()
    }

    private fun initObserver() {
        viewModel.photoList.observe(viewLifecycleOwner) { myPageInfoState ->
            when (myPageInfoState) {
                is MyPageInfoState.Uninitialized -> {
                    viewModel.getMyPageInfo()
                }

                is MyPageInfoState.Loading -> {}

                is MyPageInfoState.SuccessPhotos -> {
                    with(binding) {
                        tvMypageName.text = myPageInfoState.data.realName
                        tvMypagePictureCount.text = getString(
                            R.string.mypage_picture_count,
                            myPageInfoState.data.photoCount
                        )
                    }
                    myPageAdapter?.submitList(myPageInfoState.data.photos)
                }

                is MyPageInfoState.Error -> {}
            }
        }
    }

    private fun initRecyclerView() {
        val gridLayoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)

        myPageAdapter = MyPageAdapter(ItemDiffCallback(
            onItemsTheSame = { old, new -> old == new },
            onContentsTheSame = { old, new -> old == new }
        )) { position ->
            val photoList = viewModel.photoList.value
            if (photoList is MyPageInfoState.SuccessPhotos) {
                val itemId = photoList.data.photos?.getOrNull(position)?.photoId
                toast(itemId.toString());
                //TODO intent photo_detail activity
            }
        }

        binding.rvMypage.apply {
            layoutManager = gridLayoutManager
            adapter = myPageAdapter
            isNestedScrollingEnabled = false
        }
    }
}
