package com.teampophory.pophory.feature.share

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.teampophory.pophory.common.activity.hideLoading
import com.teampophory.pophory.common.activity.showLoading
import com.teampophory.pophory.common.view.GridSpacingItemDecoration
import com.teampophory.pophory.common.view.dp
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityShareBinding
import com.teampophory.pophory.feature.share.adapter.ShareAdapter
import com.teampophory.pophory.feature.share.model.PhotoItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityShareBinding::inflate)

    private val viewModel by viewModels<ShareViewModel>()

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            viewModel.selectedPosition?.let { binding.rvShare.adapter?.notifyItemChanged(it) }
            viewModel.selectedPosition = null
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initObserver()
    }

    private fun initObserver() {
        viewModel.photos.observe(this) { shareState ->
            when (shareState) {
                is ShareState.Uninitialized -> {
                    initRecyclerView()
                    setOnClickListener()
                }

                is ShareState.Loading -> {
                    showLoading()
                }

                is ShareState.SuccessPhoto -> {
                    hideLoading()
                    ((binding.rvShare.adapter) as? ShareAdapter)?.submitList(shareState.data)
                }

                is ShareState.Error -> {
                    hideLoading()
                }
            }
        }
    }

    private fun initRecyclerView() {
        setupLayoutManager()
        setupAdapter()
    }

    private fun setupLayoutManager() {
        val gridLayoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        with(binding) {
            rvShare.layoutManager = gridLayoutManager
            rvShare.addItemDecoration(GridSpacingItemDecoration(3, 6.dp, false))
        }
    }

    private fun setupAdapter() {
        val shareAdapter = ShareAdapter(
            onItemClicked = { photoItem, position ->
                viewModel.selectedPosition = position
                photoSharing(photoItem)
            },
            onShareSheetDismissed = {
                viewModel.selectedPosition = null
            },
            onViewRecycled = { position ->
                val viewHolder = binding.rvShare.findViewHolderForAdapterPosition(position)
                if (viewHolder is ShareAdapter.ShareViewHolder) {
                    viewHolder.binding.ivSharePhotoSelected.isVisible = false
                }
            }
        )

        binding.rvShare.apply {
            adapter = shareAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun photoSharing(photoItem: PhotoItem) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "${photoItem.photoId}")
            type = "text/plain"
        }
        startForResult.launch(Intent.createChooser(shareIntent, "외부 공유하기"))
    }

    private fun setOnClickListener() {
        binding.ivShareBack.setOnClickListener {
            finish()
        }
    }
}
