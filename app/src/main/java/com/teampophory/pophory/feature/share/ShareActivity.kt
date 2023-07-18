package com.teampophory.pophory.feature.share

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.teampophory.pophory.R
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

    private var selectedPosition: Int? = null

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        selectedPosition?.let { shareAdapter?.notifyItemChanged(it) }
        selectedPosition = null
    }

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

        shareAdapter = ShareAdapter(
            onItemClicked = { photoItem, position ->
                selectedPosition = position
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_STREAM, photoItem.imageUrl)
                    putExtra(Intent.EXTRA_TEXT, "URL 룰루랄라") // Assuming 'id' is the property of PhotoItem for the id
                    type = "text/plain"
                }
                startForResult.launch(Intent.createChooser(shareIntent, "외부 공유하기"))
            },
            onShareSheetDismissed = {
                selectedPosition = null
            }
        )

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
