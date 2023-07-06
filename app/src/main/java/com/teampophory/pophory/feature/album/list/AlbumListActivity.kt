package com.teampophory.pophory.feature.album.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.teampophory.pophory.albumsort.AlbumSortBottomSheet
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityAlbumListBinding
import com.teampophory.pophory.feature.album.list.adapter.AlbumListAdapter
import com.teampophory.pophory.feature.album.model.OrientType
import com.teampophory.pophory.feature.album.model.PhotoDetail
import com.teampophory.pophory.feature.album.model.PhotoItem
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
        initData()
        initObserver()
    }

    private fun initData() {
        val albumId = intent.getIntExtra(ALBUM_ID, 0)
        viewModel.setAlbumId(albumId)
    }

    private fun initObserver() {
        viewModel.albumList.observe(this) { albumState ->
            when (albumState) {
                is AlbumListState.Uninitialized -> {
                    initViews()
                    viewModel.getAlbums()
                }

                is AlbumListState.Loading -> {

                }

                is AlbumListState.SuccessLoadAlbums -> {
                    val photoItems = processPhotoDetails(albumState.data)
                    albumListAdapter.submitList(photoItems)
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

    private fun processPhotoDetails(photoDetails: List<PhotoDetail>): List<PhotoItem> {
        val photoItems = mutableListOf<PhotoItem>()
        val verticalItemsBuffer = mutableListOf<PhotoDetail>()

        photoDetails.forEach { photoDetail ->
            when (photoDetail.orientType) {
                OrientType.VERTICAL -> {
                    verticalItemsBuffer.add(photoDetail)
                    if (verticalItemsBuffer.size == 2) {
                        photoItems.add(PhotoItem.VerticalItem(verticalItemsBuffer.toList()))
                        verticalItemsBuffer.clear()
                    }
                }

                OrientType.HORIZONTAL -> {
                    if (verticalItemsBuffer.isNotEmpty()) {
                        verticalItemsBuffer.add(PhotoDetail(0, "", "", "", 0, 0, OrientType.NONE))
                        photoItems.add(PhotoItem.VerticalItem(verticalItemsBuffer.toList()))
                        verticalItemsBuffer.clear()
                    }
                    photoItems.add(PhotoItem.HorizontalItem(photoDetail))
                }

                OrientType.NONE -> {

                }
            }
        }

        // 마지막에 VERTICAL 타입의 사진이 한 개만 남아 있는 경우를 처리
        if (verticalItemsBuffer.isNotEmpty()) {
            verticalItemsBuffer.add(createEmptyPhotoDetail())
            photoItems.add(PhotoItem.VerticalItem(verticalItemsBuffer.toList()))
        }
        return photoItems
    }

    private fun createEmptyPhotoDetail(): PhotoDetail {
        return PhotoDetail(0, "", "", "", 0, 0, OrientType.NONE)
    }

    companion object {
        private const val TAG = "AlbumListActivity"
        const val ALBUM_ID = "ALBUM_ID"

        @JvmStatic
        fun newInstance(albumId: Int): Intent = Intent().apply {
            putExtra(ALBUM_ID, albumId)
        }
    }
}