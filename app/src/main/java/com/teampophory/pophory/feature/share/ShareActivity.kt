package com.teampophory.pophory.feature.share

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.teampophory.pophory.common.activity.hideLoading
import com.teampophory.pophory.common.activity.showLoading
import com.teampophory.pophory.common.view.GridSpacingItemDecoration
import com.teampophory.pophory.common.view.dp
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityShareBinding
import com.teampophory.pophory.feature.share.adapter.ShareAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityShareBinding::inflate)

    private var shareAdapter: ShareAdapter? = null

    private val viewModel by viewModels<ShareViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initObserver()
        initRecyclerView()
    }

    private fun initObserver() {
        viewModel.photos.observe(this) { shareState ->
            when (shareState) {
                is ShareState.Uninitialized -> {
                    viewModel.getPhotos()
                    setOnClickListener()
                }

                is ShareState.Loading -> {
                    showLoading()
                }

                is ShareState.SuccessPhoto -> {
                    hideLoading()
                    shareAdapter?.submitList(shareState.data)
                }

                is ShareState.Error -> {
                    hideLoading()
                }
            }
        }
    }

    private fun initRecyclerView() {
        val gridLayoutManager =
            GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)

        shareAdapter = ShareAdapter { photos ->
            //TODO new intent for 의진
        }

        binding.rvShare.apply {
            layoutManager = gridLayoutManager
            adapter = shareAdapter
            isNestedScrollingEnabled = false
        }.addItemDecoration(GridSpacingItemDecoration(3, 6.dp, false))
    }

    private fun setOnClickListener() {
        binding.ivShareBack.setOnClickListener {
            finish()
        }
    }

}
