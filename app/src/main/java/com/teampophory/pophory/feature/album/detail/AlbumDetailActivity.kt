package com.teampophory.pophory.feature.album.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.teampophory.pophory.common.context.toast
import com.teampophory.pophory.common.intent.intExtra
import com.teampophory.pophory.common.intent.stringExtra
import com.teampophory.pophory.common.view.showAllowingStateLoss
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityAlbumDetailBinding
import com.teampophory.pophory.feature.album.model.PhotoDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumDetailActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityAlbumDetailBinding::inflate)
    private val viewModel by viewModels<AlbumDetailViewModel>()

    private val photoId by intExtra(0)
    private val studio by stringExtra("")
    private val takenAt by stringExtra("")
    private val imageUrl by stringExtra("")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initObserver()
    }

    private fun initObserver() {
        viewModel.albumDetailState.observe(this) {
            when (it) {
                AlbumDetailState.Uninitialized -> {
                    initData()
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

    private fun initData() {
        viewModel.setPhotoId(photoId)
        viewModel.setStudio(studio ?: "")
        viewModel.setTakenAt(takenAt ?: "")
        viewModel.setImageUrl(imageUrl ?: "")
        initViews()
    }

    private fun initViews() {
        initButtonClickListener()
        with(binding) {
            ivMainDetailAlbum.load(viewModel.imageUrl.value)
            tvAlbumTakenAt.text = viewModel.takenAt.value
            tvStudio.text = viewModel.studio.value
        }
    }

    companion object {
        private const val PHOTO_ID = "photoId"
        private const val STUDIO = "studio"
        private const val TAKEN_AT = "takenAt"
        private const val IMAGE_URL = "imageUrl"
        fun startActivity(context: Context, photoDetail: PhotoDetail) {
            Intent(context, AlbumDetailActivity::class.java).apply {
                putExtra(PHOTO_ID, photoDetail.id)
                putExtra(STUDIO, photoDetail.studio)
                putExtra(TAKEN_AT, photoDetail.takenAt)
                putExtra(IMAGE_URL, photoDetail.imageUrl)
            }.let(context::startActivity)
        }
    }
}