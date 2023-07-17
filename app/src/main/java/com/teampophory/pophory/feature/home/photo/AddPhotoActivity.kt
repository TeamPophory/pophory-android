package com.teampophory.pophory.feature.home.photo

import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.teampophory.pophory.R
import com.teampophory.pophory.common.activity.BindingActivity
import com.teampophory.pophory.common.context.colorOf
import com.teampophory.pophory.common.context.snackBar
import com.teampophory.pophory.common.context.toast
import com.teampophory.pophory.common.image.BitmapRequestBody
import com.teampophory.pophory.common.image.getAllocatedByte
import com.teampophory.pophory.common.image.getImageSize
import com.teampophory.pophory.common.intent.parcelableExtra
import com.teampophory.pophory.common.intent.stringExtra
import com.teampophory.pophory.common.time.systemNow
import com.teampophory.pophory.common.view.setOnSingleClickListener
import com.teampophory.pophory.databinding.ActivityAddPhotoBinding
import com.teampophory.pophory.feature.home.store.model.AlbumItem
import com.teampophory.pophory.util.toCoverDrawable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Instant
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class AddPhotoActivity : BindingActivity<ActivityAddPhotoBinding>(R.layout.activity_add_photo) {
    private val viewModel: AddPhotoViewModel by viewModels()
    private val imageUri by stringExtra()
    private val albumItem by parcelableExtra<AlbumItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadImage()
        initView()
        subscribeEvent()
        collectState()
    }

    private fun loadImage() {
        val realImageUri = Uri.parse(imageUri)
        val imageSize = realImageUri?.getImageSize(this)
        imageSize?.let {
            val imageMB = realImageUri.getAllocatedByte(this) / (1024 * 1024.0)
            val imageSource = ImageDecoder.createSource(contentResolver, realImageUri)
            val bitmap = ImageDecoder.decodeBitmap(imageSource)
            val imageRequestBody = if (imageMB >= 3) {
                // Get compress rate of image from uri, compress rate is current 3MB / image byte
                val compressRate = ((3 / imageMB) * 100).toInt()
                BitmapRequestBody(bitmap, compressRate)
            } else {
                BitmapRequestBody(bitmap)
            }
            viewModel.onUpdateImage(imageRequestBody, it)
            if (it.width >= it.height) {
                binding.imgBackground.setImageResource(R.drawable.img_background_width)
                binding.imgHorizontal.load(realImageUri) {
                    crossfade(true)
                }
                return
            }
            binding.imgBackground.setImageResource(R.drawable.img_background_height)
            binding.imgVertical.load(realImageUri) {
                crossfade(true)
            }
        }
    }

    private fun initView() {
        binding.toolbarAddPhoto.btnBack.setOnClickListener {
            finish()
        }
        binding.toolbarAddPhoto.txtToolbarTitle.text = "사진 추가"
        binding.layoutDate.setOnClickListener {
            viewModel.onCreatedAtPressed()
        }
        binding.layoutStudio.setOnClickListener {
            viewModel.onStudioPressed()
        }
        albumItem?.albumCover?.toCoverDrawable()
            ?.let { binding.imgAlbumCover.setImageResource(it) }
        binding.btnSubmit.setOnSingleClickListener {
            viewModel.onSubmit()
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
                                        DateValidatorPointBackward.now()
                                    )
                                    .setEnd(Instant.systemNow().toEpochMilliseconds()).build()
                            )
                            .setSelection(
                                currentCreatedAt + TimeZone.getDefault().getOffset(currentCreatedAt)
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
                        "yyyy.MM.dd(EEEEE)",
                        Locale.getDefault()
                    ).format(Date(it))
            }.launchIn(lifecycleScope)
        viewModel.currentStudio
            .flowWithLifecycle(lifecycle)
            .onEach {
                if (it.isNotEmpty()) {
                    binding.txtStudio.text = it.joinToString { studio -> studio.name }
                    binding.txtStudio.setTextColor(colorOf(R.color.pophory_black))
                } else {
                    binding.txtStudio.text = "사진관을 선택해주세요"
                    binding.txtStudio.setTextColor(colorOf(R.color.gray_40))
                }
            }.launchIn(lifecycleScope)
    }

    companion object {
        @JvmStatic
        fun getIntent(context: Context, imageUri: String, albumItem: AlbumItem): Intent =
            Intent(context, AddPhotoActivity::class.java).apply {
                putExtra("imageUri", imageUri)
                putExtra("albumItem", albumItem)
            }

        private const val BYTE_UNIT = 4L
    }
}
