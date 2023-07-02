package com.teampophory.pophory.feature.home.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.teampophory.pophory.R
import com.teampophory.pophory.common.view.viewBinding
import androidx.fragment.app.viewModels
import com.teampophory.pophory.databinding.FragmentStoreBinding
import com.teampophory.pophory.feature.home.store.apdater.StoreAdapter

class StoreFragment : Fragment() {
    private val binding by viewBinding(FragmentStoreBinding::bind)

    private lateinit var adapter: StoreAdapter
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
        adapter = StoreAdapter { position ->
            //val intent = Intent(context, DetailActivity::class.java)
            //startActivity(intent)
        }
        binding.viewpagerStore.adapter = adapter
        adapter.submitList(viewModel.mockAlbumList)

        //1차 스프린트용 입력
        binding.viewpagerStore.isUserInputEnabled = false
    }
}
