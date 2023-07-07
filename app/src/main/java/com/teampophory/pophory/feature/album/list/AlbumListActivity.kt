package com.teampophory.pophory.feature.album.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.teampophory.pophory.R
import com.teampophory.pophory.albumsort.AlbumSortBottomSheet
import com.teampophory.pophory.albumsort.AlbumSortType
import com.teampophory.pophory.common.activity.hideLoading
import com.teampophory.pophory.common.activity.showLoading
import com.teampophory.pophory.common.context.stringOf
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityAlbumListBinding
import com.teampophory.pophory.feature.album.list.adapter.AlbumListAdapter
import com.teampophory.pophory.feature.home.photo.AddPhotoActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class AlbumListActivity : AppCompatActivity() {

    private val albumListAdapter = AlbumListAdapter()
    private val viewModel by viewModels<AlbumListViewModel>()
    private val binding by viewBinding(ActivityAlbumListBinding::inflate)

    private val imagePicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        val currentAlbum = viewModel.currentAlbum.value
        val intent = currentAlbum?.let { albumItem ->
            AddPhotoActivity.getIntent(this, uri.toString(), albumItem)
        }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initData()
        initObserver()
    }

    private fun initData() {
        val albumId = intent.getIntExtra(ALBUM_ID, 0)
        viewModel.setAlbumId(albumId)
    }

    private fun initObserver() {
        viewModel.albumListState.flowWithLifecycle(lifecycle).onEach { albumState ->
            when (albumState) {
                is AlbumListState.Uninitialized -> {
                    initViews()
                    viewModel.getAlbums()
                }

                is AlbumListState.Loading -> {
                    showLoading()
                }

                is AlbumListState.SuccessLoadAlbums -> {
                    hideLoading()
                    val photoItems = albumState.data
                    if (photoItems.isNotEmpty()) {
                        binding.clEmptyView.isVisible = false
                        albumListAdapter.submitList(photoItems)
                    } else {
                        binding.clEmptyView.isVisible = true
                    }
                    Timber.tag(TAG).d("≈≈≈≈≈≈≈: %s", albumState.data)
                }

                is AlbumListState.Error -> {
                    hideLoading()
                    binding.clEmptyView.isVisible = true
                    Timber.tag(TAG).e(albumState.error)
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun initViews() {
        initSortViews()
        initToolbar()
        initRecyclerView()
    }

    private fun initToolbar() {
        binding.ivAlbumAdd.setOnClickListener {
            imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun initSortViews() {
        viewModel.albumSortType.flowWithLifecycle(lifecycle).onEach {
            binding.tvSort.text = when (it) {
                AlbumSortType.NEWEST -> {
                    stringOf(R.string.sort_newest)
                }

                AlbumSortType.OLDEST -> {
                    stringOf(R.string.sort_oldest)
                }
            }
        }.launchIn(lifecycleScope)
        showAlbumSortBottomSheet()
    }

    private fun showAlbumSortBottomSheet() {
        val modalBottomSheet = AlbumSortBottomSheet()
        binding.tvSort.setOnClickListener {
            modalBottomSheet.show(supportFragmentManager, AlbumSortBottomSheet.TAG)
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
        const val ALBUM_ID = "ALBUM_ID"

        @JvmStatic
        fun newInstance(context: Context, albumId: Int): Intent =
            Intent(context, AlbumListActivity::class.java).apply {
                putExtra(ALBUM_ID, albumId)
            }
    }
}