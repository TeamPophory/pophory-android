package com.teampophory.pophory.feature.album

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.teampophory.pophory.common.activity.hideLoading
import com.teampophory.pophory.common.activity.showLoading
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityAlbumListBinding
import com.teampophory.pophory.feature.album.adapter.AlbumAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
        viewModel.albumList.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).onEach { albumState ->
            when (albumState) {
                is AlbumState.Uninitialized -> {
                    initRecyclerView()
                    viewModel.getAlbums()
                }

                is AlbumState.Loading -> {
                    showLoading()
                }

                is AlbumState.SuccessAlbums -> {
                    hideLoading()
                    albumAdapter.submitList(albumState.data)
                }

                is AlbumState.Error -> {
                    hideLoading()
                    //TODO ERROR 처리
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun initRecyclerView() {
        binding.rvAlbum.apply {
            layoutManager =
                LinearLayoutManager(this@AlbumListActivity, LinearLayoutManager.VERTICAL, false)
            adapter = albumAdapter
        }
    }
}