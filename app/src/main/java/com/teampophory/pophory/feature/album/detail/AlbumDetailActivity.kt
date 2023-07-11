package com.teampophory.pophory.feature.album.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.teampophory.pophory.common.context.toast
import com.teampophory.pophory.common.view.showAllowingStateLoss
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityAlbumDetailBinding
import com.teampophory.pophory.feature.album.model.PhotoDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumDetailActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityAlbumDetailBinding::inflate)
    private val viewModel by viewModels<AlbumDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()
        initObserver()
    }

    override fun onDestroy() {
        supportFinishAfterTransition();
        super.onDestroy()
    }

    private fun initObserver() {
        viewModel.albumDetailState.observe(this) {
            when (it) {
                AlbumDetailState.Uninitialized -> {
                    initViews()
                }

                is AlbumDetailState.SuccessDeleteAlbum -> {
                    supportFragmentManager.findFragmentByTag(AlbumDeleteDialogFragment.TAG)
                        ?.let { fragment ->
                            (fragment as? AlbumDeleteDialogFragment)?.dismissAllowingStateLoss()
                        }
                    toast("앨범이 삭제되었습니다.")
                    finish()
                }

                is AlbumDetailState.Error -> {
                    toast(it.message)
                }
            }
        }
    }

    private fun initButtonClickListener() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.ivAlbumDelete.setOnClickListener {
            AlbumDeleteDialogFragment.newInstance().apply {
                showAllowingStateLoss(supportFragmentManager, AlbumDeleteDialogFragment.TAG)
            }
        }
    }

    private fun initViews() {
        initButtonClickListener()
        with(binding) {
            val photoDetailInfo = viewModel.photoDetail.value
            ivMainDetailAlbum.load(photoDetailInfo?.imageUrl)
            tvAlbumTakenAt.text = photoDetailInfo?.takenAt.orEmpty()
            tvStudio.text = photoDetailInfo?.studio.orEmpty()
        }
    }

    companion object {
        fun newIntent(context: Context, photoDetail: PhotoDetail): Intent {
            return Intent(context, AlbumDetailActivity::class.java).apply {
                putExtra("photoId", photoDetail.id)
                putExtra("studio", photoDetail.studio)
                putExtra("takenAt", photoDetail.takenAt)
                putExtra("imageUrl", photoDetail.imageUrl)
            }
        }
    }
}