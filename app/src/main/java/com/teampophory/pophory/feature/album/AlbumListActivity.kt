package com.teampophory.pophory.feature.album

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityAlbumListBinding
import com.teampophory.pophory.feature.album.adapter.AlbumAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                    albumAdapter.submitList(albumState.data)
                }

                is AlbumState.Error -> {}
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvAlbum.apply {
            layoutManager =
                LinearLayoutManager(this@AlbumListActivity, LinearLayoutManager.VERTICAL, false)
            adapter = albumAdapter
        }
    }
}