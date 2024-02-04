package com.teampophory.pophory.feature.album.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.iosParameters
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase
import com.teampophory.pophory.common.context.toast
import com.teampophory.pophory.common.view.setOnSingleClickListener
import com.teampophory.pophory.common.view.showAllowingStateLoss
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityAlbumDetailBinding
import com.teampophory.pophory.feature.album.model.PhotoDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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
        supportFinishAfterTransition()
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
                    toast("사진이 삭제되었습니다.")
                    setResult(RESULT_OK)
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

        binding.ivAlbumDelete.setOnSingleClickListener {
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
            if (photoDetailInfo?.studio.orEmpty() == "NONE") {
                tvStudio.isVisible = false
            }
            ivAlbumShare.setOnSingleClickListener {
                lifecycleScope.launch {
                    runCatching {
                        Firebase.dynamicLinks.shortLinkAsync {
                            link =
                                Uri.parse("https://pophory.page.link/share?u=${photoDetailInfo?.shareId}")
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
                            startActivity(
                                Intent.createChooser(
                                    it,
                                    link.shortLink.toString(),
                                ),
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun newIntent(context: Context, photoDetail: PhotoDetail): Intent {
            return Intent(context, AlbumDetailActivity::class.java).apply {
                putExtra("photoId", photoDetail.id)
                putExtra("studio", photoDetail.studio)
                putExtra("takenAt", photoDetail.takenAt)
                putExtra("imageUrl", photoDetail.imageUrl)
                putExtra("shareId", photoDetail.shareId)
            }
        }
    }
}
