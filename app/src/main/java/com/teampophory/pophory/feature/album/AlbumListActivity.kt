package com.teampophory.pophory.feature.album

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityAlbumListBinding
import com.teampophory.pophory.feature.album.adapter.AlbumAdapter

class AlbumListActivity : AppCompatActivity() {

    private val albumAdapter = AlbumAdapter()
    private val viewModel by viewModels<AlbumListViewModel>()
    private val binding by viewBinding(ActivityAlbumListBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initObserver()
    }

    private fun initObserver() {
        viewModel.albumList.observe(this) { albumState ->
            when (albumState) {
                is AlbumState.Uninitialized -> {
                    initRecyclerView()
                    viewModel.getAlbums()
                }

                is AlbumState.Loading -> {}

                is AlbumState.SuccessAlbums -> {
                    albumAdapter.submitList(albumState.data.photos)
                }

                is AlbumState.Error -> {}
            }
        }
    }

    private fun initRecyclerView() {
        val flexboxLayoutManager = FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
        }

        binding.rvAlbum.apply {
            layoutManager = flexboxLayoutManager
            adapter = albumAdapter
        }
    }
}