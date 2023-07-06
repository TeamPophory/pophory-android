package com.teampophory.pophory.feature.home.photo

import android.content.Context
import android.content.Intent
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
import com.teampophory.pophory.common.image.getImageSize
import com.teampophory.pophory.common.intent.intExtra
import com.teampophory.pophory.common.intent.parcelableExtra
import com.teampophory.pophory.common.time.systemNow
import com.teampophory.pophory.databinding.ActivityAddPhotoBinding
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
    private val imageUri by parcelableExtra<Uri>()
    private val albumCoverId by intExtra()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadImage()
        initView()
        subscribeEvent()
        collectState()
    }

    private fun loadImage() {
        val imageSize = imageUri?.getImageSize()
        imageSize?.let {
            if (it.width >= it.height) {
                binding.imgBackground.load(R.drawable.img_background_width)
                binding.imgHorizontal.load(imageUri) {
                    crossfade(true)
                }
                return
            }
            binding.imgBackground.load(R.drawable.img_background_height)
            binding.imgVertical.load(imageUri) {
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
    }

    companion object {
        @JvmStatic
        fun getIntent(context: Context, imageUri: Uri, albumCoverId: Int) =
            Intent(context, AddPhotoActivity::class.java).apply {
                putExtra("imageUri", imageUri)
                putExtra("albumCoverId", albumCoverId)
            }
    }
}
