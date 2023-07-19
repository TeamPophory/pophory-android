package com.teampophory.pophory.feature.share

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase
import com.teampophory.pophory.common.activity.hideLoading
import com.teampophory.pophory.common.activity.showLoading
import com.teampophory.pophory.common.view.GridSpacingItemDecoration
import com.teampophory.pophory.common.view.dp
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityShareBinding
import com.teampophory.pophory.feature.share.adapter.ShareAdapter
import com.teampophory.pophory.feature.share.model.PhotoItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber

@AndroidEntryPoint
class ShareActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityShareBinding::inflate)

    private val viewModel by viewModels<ShareViewModel>()

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
                }.await()
            }.onSuccess { link ->
                Timber.d("Pophory dynamicLink $link shortLink ${link.shortLink}")
                Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, link.shortLink.toString())
                }.also {
                    startActivity(
                        Intent.createChooser(
                            it,
                            link.shortLink.toString()
                        )
                    )
                }
            }
        }

    }

    private fun setOnClickListener() {
        binding.ivShareBack.setOnClickListener {
            finish()
        }
    }
}
