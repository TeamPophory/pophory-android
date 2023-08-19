package com.teampophory.pophory.feature.share.receive

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.TaskStackBuilder
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.teampophory.pophory.common.activity.hideLoading
import com.teampophory.pophory.common.activity.showLoading
import com.teampophory.pophory.common.context.snackBar
import com.teampophory.pophory.common.context.toast
import com.teampophory.pophory.common.navigation.NavigationProvider
import com.teampophory.pophory.common.view.setOnSingleClickListener
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.feature.share.databinding.ActivityReceiveSharedImageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ReceiveSharedImageActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityReceiveSharedImageBinding::inflate)
    private val viewModel by viewModels<ReceiveImageViewModel>()

    @Inject
    lateinit var navigationProvider: NavigationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lifecycleScope.launch {
            runCatching {
                Firebase.dynamicLinks.getDynamicLink(intent).await()
            }.onSuccess { dynamicLink ->
                dynamicLink.link?.getQueryParameter("u")?.let {
                    viewModel.loadUiData(it)
                }
            }.onFailure {
                Timber.e(it)
                finish()
            }
        }

        binding.btnAccept.setOnSingleClickListener {
            viewModel.onAccepted()
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
                        binding.txtNickname.text = "@${it.nickName}"
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
                                val invisibleImageView = if (width >= height) {
                                    binding.imgVertical
                                } else {
                                    binding.imgHorizontal
                                }
                                with(imageView) {
                                    isVisible = true
                                    load(image)
                                }
                                invisibleImageView.isVisible = false
                            }.build()
                        imageLoader.enqueue(request)
                    }

                    is ReceiveImageUiState.AcceptedSharPhotoData -> {
                        hideLoading()
                        TaskStackBuilder.create(this).apply {
                            addNextIntent(navigationProvider.toHome())
                            addNextIntent(navigationProvider.toAlbumList(it.albumId))
                        }.also { stack ->
                            stack.startActivities()
                        }
                    }

                    is ReceiveImageUiState.SignUp -> {
                        hideLoading()
                        startActivity(navigationProvider.toOnboarding())
                    }

                    is ReceiveImageUiState.Error -> {
                        hideLoading()
                        val exception = it.exception
                        if (exception is HttpException) {
                            val httpException: HttpException = exception
                            if (httpException.code() != 400) {
                                snackBar(binding.root) {
                                    "사진 등록 시 네트워크에 문제가 발생했습니다."
                                }
                            } else {
                                runCatching {
                                    val errorBody = parseErrorBody(httpException)
                                    Json.parseToJsonElement(errorBody).jsonObject
                                }.onSuccess { parsedBody ->
                                    if (!parsedBody.containsKey("code")) {
                                        snackBar(binding.root) {
                                            "사진 등록 시 네트워크에 문제가 발생했습니다."
                                        }
                                    } else {
                                        val errorCode = parsedBody["code"]?.jsonPrimitive?.intOrNull
                                        when (errorCode) {
                                            ALBUM_LIMIT_EXCEPTION_CODE -> {
                                                snackBar(binding.root) {
                                                    "앨범에 사진을 바운 이후 다시 이용해주세요."
                                                }
                                            }

                                            SELF_APPROVE_EXCEPTION_CODE -> {
                                                snackBar(binding.root) {
                                                    "이미 내 앨범에 있는 사진이에요."
                                                }
                                            }

                                            else -> {
                                                snackBar(binding.root) {
                                                    "앨범에 사진 등록 시 네트워크에 문제가 발생했습니다."
                                                }
                                            }
                                        }
                                    }
                                }.onFailure {
                                    toast("사진 정보를 가져올 때 문제가 발생했습니다.")
                                    finish()
                                }
                            }
                        }
                    }
                }
            }.launchIn(lifecycleScope)
    }

    private fun parseErrorBody(exception: HttpException): String {
        return exception.response()?.errorBody()?.string() ?: ""
    }

    private companion object {
        const val ALBUM_LIMIT_EXCEPTION_CODE = 442
        const val SELF_APPROVE_EXCEPTION_CODE = 4423
    }
}
