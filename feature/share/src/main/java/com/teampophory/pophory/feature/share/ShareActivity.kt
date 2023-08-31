package com.teampophory.pophory.feature.share

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.iosParameters
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase
import com.teampophory.pophory.common.activity.hideLoading
import com.teampophory.pophory.common.activity.showLoading
import com.teampophory.pophory.common.view.GridSpacingItemDecoration
import com.teampophory.pophory.common.view.dp
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.feature.share.adapter.ShareAdapter
import com.teampophory.pophory.feature.share.databinding.ActivityShareBinding
import com.teampophory.pophory.feature.share.model.PhotoItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@AndroidEntryPoint
class ShareActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityShareBinding::inflate)

    private val viewModel by viewModels<ShareViewModel>()

    private lateinit var shareResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        shareResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                clearSelectedPosition()
            }

        initObserver()
    }

    private fun initObserver() {
        viewModel.photos.observe(this) { shareState ->
            when (shareState) {
                is ShareState.Uninitialized -> {
                    initRecyclerView()
                    setOnClickListener()
                    viewModel.getPhotos()
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
            }
        )

        binding.rvShare.apply {
            adapter = shareAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun photoSharing(photoItem: PhotoItem) {
        lifecycleScope.launch {
            runCatching {
                Firebase.dynamicLinks.shortLinkAsync {
                    link = Uri.parse("https://pophory.page.link/share?u=${photoItem.shareId}")
                    domainUriPrefix = "https://pophory.page.link"
                    androidParameters("com.teampophory.pophory") {}
                    iosParameters("Team.pophory-iOS") {
                        appStoreId = "6451004060"
                    }
                }.await()
            }.onSuccess { link ->
                Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, link.shortLink.toString())
                }.also {
                    shareResultLauncher.launch(
                        Intent.createChooser(
                            it,
                            link.shortLink.toString()
                        )
                    )
                }
            }
        }
    }

    private fun clearSelectedPosition() {
        val position = viewModel.selectedPosition
        viewModel.selectedPosition = null
        if (position != null) {
            val holder =
                binding.rvShare.findViewHolderForAdapterPosition(position) as? ShareAdapter.ShareViewHolder
            holder?.clear()
        }
    }

    private fun setOnClickListener() {
        binding.ivShareBack.setOnClickListener {
            finish()
        }
    }
}