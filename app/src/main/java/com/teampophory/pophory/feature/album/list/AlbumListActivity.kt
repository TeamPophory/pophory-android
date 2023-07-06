package com.teampophory.pophory.feature.album.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.teampophory.pophory.bottomsheet.ModalBottomSheet
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityAlbumListBinding
import com.teampophory.pophory.feature.album.list.adapter.AlbumListAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AlbumListActivity : AppCompatActivity() {

    private val albumListAdapter = AlbumListAdapter()
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
                is AlbumListState.Uninitialized -> {
                    initViews()
                    viewModel.getAlbums(0)
                }

                is AlbumListState.Loading -> {

                }

                is AlbumListState.SuccessAlbums -> {
                    albumListAdapter.submitList(albumState.data)
                    Timber.tag(TAG).d("initObserver: %s", albumState.data)
                }

                is AlbumListState.Error -> {
                    Timber.tag(TAG).e(albumState.error)
                }
            }
        }
    }

    private fun initViews() {
        initSortViews()
        initToolbar()
        initRecyclerView()
    }

    private fun initToolbar() {
        binding.ivAlbumAdd.setOnClickListener {
            //TODO 사진 찍는 플로우 추가
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun initSortViews() {
        val modalBottomSheet = ModalBottomSheet()
        binding.tvSort.setOnClickListener {
            modalBottomSheet.show(supportFragmentManager, ModalBottomSheet.TAG)
        }
    }

    private fun initRecyclerView() {
        binding.rvAlbum.apply {
            layoutManager =
                LinearLayoutManager(this@AlbumListActivity, LinearLayoutManager.VERTICAL, false)
            adapter = albumListAdapter
        }
    }

    companion object {
        private const val TAG = "AlbumListActivity"
    }
}