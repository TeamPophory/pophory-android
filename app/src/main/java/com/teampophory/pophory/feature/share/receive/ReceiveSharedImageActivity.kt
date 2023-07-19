package com.teampophory.pophory.feature.share.receive

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.teampophory.pophory.common.activity.hideLoading
import com.teampophory.pophory.common.activity.showLoading
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityReceiveSharedImageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber

@AndroidEntryPoint
class ReceiveSharedImageActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityReceiveSharedImageBinding::inflate)
    private val viewModel by viewModels<ReceiveImageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lifecycleScope.launch {
            runCatching {
                Firebase.dynamicLinks.getDynamicLink(intent).await()
            }.onSuccess { dynamicLink ->
                dynamicLink.link?.getQueryParameter("u")?.let {
                    Timber.d("Pophory uuid $it")
                    viewModel.loadUiData(it)
                }
            }.onFailure {
                Timber.e(it)
                finish()
            }
        }

        viewModel.uiState
            .flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is ReceiveImageUiState.Init -> {
                        hideLoading()
                    }

                    is ReceiveImageUiState.Loading -> {
                        showLoading()
                    }

                    is ReceiveImageUiState.Photo -> {
                        hideLoading()
                        binding.txtName.text = it.realName
                        binding.txtNickname.text = it.nickName
                        val request = ImageRequest.Builder(this)
                            .data(it.imageUrl)
                            .crossfade(true)
                            .target { image ->
                                val width = image.intrinsicWidth
                                val height = image.intrinsicHeight
                                val imageView = if (width >= height) {
                                    binding.imgHorizontal
                                } else {
                                    binding.imgVertical
                                }
                                imageView.load(image)
                            }.build()
                        imageLoader.enqueue(request)
                    }

                    is ReceiveImageUiState.Accepted -> {
                        hideLoading()
                    }

                    is ReceiveImageUiState.Error -> {
                        hideLoading()
                    }
                }
            }.launchIn(lifecycleScope)
    }
}
