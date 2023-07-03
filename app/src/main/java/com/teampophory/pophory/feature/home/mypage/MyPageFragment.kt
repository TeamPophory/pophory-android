package com.teampophory.pophory.feature.home.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.teampophory.pophory.R
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
        viewModel.photoList.observe(viewLifecycleOwner) { photoState ->
            when (photoState) {
                is PhotoState.Uninitialized -> {
                    viewModel.getPhotos()
                }

                is PhotoState.Loading -> {}

                is PhotoState.SuccessPhotos -> {
                    myPageAdapter?.submitList(photoState.data.photos)
                }

                is PhotoState.Error -> {}
            }
        }
    }

    private fun initRecyclerView() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)

        myPageAdapter = MyPageAdapter(ItemDiffCallback(
            onItemsTheSame = { old, new -> old == new },
            onContentsTheSame = { old, new -> old == new }
        )) { position ->
//            val intent = Intent(context, AlbumListActivity::class.java)
//            intent.putExtra("itemId", position)
//            requireContext().startActivity(intent)
        }

        binding.rvMypage.apply {
            layoutManager = gridLayoutManager
            adapter = myPageAdapter
            isNestedScrollingEnabled = false
        }
    }
}
