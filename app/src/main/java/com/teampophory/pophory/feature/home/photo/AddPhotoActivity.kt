package com.teampophory.pophory.feature.home.photo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Size
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.teampophory.pophory.BuildConfig
import com.teampophory.pophory.R
import com.teampophory.pophory.common.context.colorOf
import com.teampophory.pophory.common.context.snackBar
import com.teampophory.pophory.common.context.toast
import com.teampophory.pophory.common.image.ContentUriRequestBody
import com.teampophory.pophory.common.image.getAdjustedSize
import com.teampophory.pophory.common.intent.stringExtra
import com.teampophory.pophory.common.time.systemNow
import com.teampophory.pophory.common.view.setOnSingleClickListener
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityAddPhotoBinding
import com.teampophory.pophory.feature.home.model.RegisterNavigationType
import com.teampophory.pophory.feature.home.store.model.AlbumItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Instant
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class AddPhotoActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityAddPhotoBinding::inflate)
    private val viewModel: AddPhotoViewModel by viewModels()
    private val imageUri by stringExtra()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadImage()
        initView()
        subscribeEvent()
        collectState()
    }

    private fun loadImage() {
        val realImageUri = Uri.parse(imageUri)
        val adjustedSize = realImageUri.getAdjustedSize(this)

        val imageRequestBody = ContentUriRequestBody(this, realImageUri)
        viewModel.onUpdateImage(imageRequestBody, adjustedSize)

        loadImageWithAdjustedSize(realImageUri, adjustedSize)
    }

    private fun loadImageWithAdjustedSize(realImageUri: Uri, adjustedSize: Size) {
        val (backgroundResource, imageView) = if (adjustedSize.width >= adjustedSize.height) {
            R.drawable.img_background_width to binding.imgHorizontal
        } else {
            R.drawable.img_background_height to binding.imgVertical
        }
        binding.imgBackground.setImageResource(backgroundResource)
        imageView.load(realImageUri) {
            crossfade(true)
        }
    }

    private fun initView() {
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.txtToolbarTitle.text = "사진 추가"
        binding.layoutDate.setOnClickListener {
            viewModel.onCreatedAtPressed()
        }
        binding.layoutStudio.setOnClickListener {
            viewModel.onStudioPressed()
        }
        binding.btnSubmit.setOnSingleClickListener {
            viewModel.onSubmit()
        }
        onBackPressedDispatcher.addCallback(this) {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
        binding.btnShare.setOnSingleClickListener {
            val intent = Intent("com.instagram.share.ADD_TO_STORY").apply {
                putExtra("source_application", BuildConfig.FACEBOOK_APP_ID)
                type = "image/png"
                putExtra("interactive_asset_uri", imageUri)
                putExtra("top_background_color", "#FF6724")
                putExtra("bottom_background_color", "#FF6724")
            }
        }
    }

    private fun subscribeEvent() {
        viewModel.event
            .flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    AddPhotoEvent.DATE -> {
                        val currentCreatedAt = viewModel.createdAt.value
                        val picker = MaterialDatePicker.Builder
                            .datePicker()
                            .setTheme(R.style.ThemeOverlay_Pophory_MaterialCalendar)
                            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                            .setCalendarConstraints(
                                CalendarConstraints.Builder()
                                    .setValidator(
                                        DateValidatorPointBackward.now(),
                                    )
                                    .setEnd(Instant.systemNow().toEpochMilliseconds()).build(),
                            )
                            .setSelection(
                                currentCreatedAt + TimeZone.getDefault().getOffset(currentCreatedAt),
                            )
                            .build()
                        picker.show(supportFragmentManager, "datePicker")
                        picker.addOnPositiveButtonClickListener(viewModel::onUpdateCreateAt)
                    }

                    AddPhotoEvent.STUDIO -> {
                        StudioSelectFragment().show(supportFragmentManager, "studio")
                    }

                    AddPhotoEvent.REQUEST_ERROR -> {
                        snackBar(binding.root) { "요청에 실패했습니다." }
                    }

                    AddPhotoEvent.ADD_SUCCESS -> {
                        setResult(RESULT_OK, intent)
                        finish()
                        toast("사진이 추가되었습니다.")
                    }
                }
            }.launchIn(lifecycleScope)
    }

    private fun collectState() {
        viewModel.createdAt
            .flowWithLifecycle(lifecycle)
            .onEach {
                binding.txtDate.text =
                    SimpleDateFormat(
                        "yyyy.MM.dd",
                        Locale.getDefault(),
                    ).format(Date(it))
            }.launchIn(lifecycleScope)
        viewModel.currentStudio
            .flowWithLifecycle(lifecycle)
            .onEach {
                if (it.isNotEmpty()) {
                    binding.txtStudio.text = it.joinToString { studio -> studio.name }
                    binding.txtStudio.setTextColor(colorOf(com.teampophory.pophory.designsystem.R.color.pophory_black))
                } else {
                    binding.txtStudio.text = "사진관을 선택해주세요"
                    binding.txtStudio.setTextColor(colorOf(com.teampophory.pophory.designsystem.R.color.gray_40))
                }
            }.launchIn(lifecycleScope)
        viewModel.type
            .flowWithLifecycle(lifecycle)
            .onEach {
                binding.btnShare.isVisible = it != RegisterNavigationType.PICKER
            }.launchIn(lifecycleScope)
    }

    companion object {
        private const val IMAGE_URL_EXTRA = "imageUri"
        const val ALBUM_ITEM_EXTRA = "albumItem"
        const val IMAGE_MIME_TYPE = "image/*"
        private const val TYPE = "type"

        @JvmStatic
        fun getIntent(
            context: Context,
            imageUri: String,
            albumItem: AlbumItem,
            type: String,
        ): Intent =
            Intent(context, AddPhotoActivity::class.java).apply {
                putExtra(IMAGE_URL_EXTRA, imageUri)
                putExtra(ALBUM_ITEM_EXTRA, albumItem)
                putExtra(TYPE, type)
            }
    }
}
