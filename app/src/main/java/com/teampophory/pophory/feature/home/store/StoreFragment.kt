package com.teampophory.pophory.feature.home.store

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.teampophory.pophory.R
import com.teampophory.pophory.common.view.viewBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.teampophory.pophory.databinding.FragmentStoreBinding
import com.teampophory.pophory.feature.album.AlbumListActivity
import com.teampophory.pophory.feature.home.store.apdater.StoreAdapter

class StoreFragment : Fragment() {
    private val binding by viewBinding(FragmentStoreBinding::bind)

    private var adapter: StoreAdapter? = null
    private val viewModel by viewModels<StoreViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_store, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {

        //2차 스프린트를 위해 position 값을 받아둠
        adapter = StoreAdapter { position ->
            val intent = Intent(context, AlbumListActivity::class.java)
            intent.putExtra("itemId", position)
            requireContext().startActivity(intent)
        }
        binding.viewpagerStore.adapter = adapter

        viewModel.photoList.observe(viewLifecycleOwner, Observer { list ->
            adapter?.submitList(list)
        })

        //1차 스프린트용 입력 방지
        binding.viewpagerStore.isUserInputEnabled = false
    }
}
