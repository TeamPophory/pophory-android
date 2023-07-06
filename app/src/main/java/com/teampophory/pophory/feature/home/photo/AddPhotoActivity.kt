package com.teampophory.pophory.feature.home.photo

import android.os.Bundle
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.teampophory.pophory.R
import com.teampophory.pophory.common.activity.BindingActivity
import com.teampophory.pophory.common.time.systemNow
import com.teampophory.pophory.databinding.ActivityAddPhotoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.datetime.Instant

@AndroidEntryPoint
class AddPhotoActivity : BindingActivity<ActivityAddPhotoBinding>(R.layout.activity_add_photo) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.layoutDate.setOnClickListener {
            MaterialDatePicker.Builder
                .datePicker()
                .setTheme(R.style.ThemeOverlay_Pophory_MaterialCalendar)
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                .setCalendarConstraints(
                    CalendarConstraints.Builder()
                        .setEnd(Instant.systemNow().toEpochMilliseconds())
                        .build()
                )
                .build()
                .show(supportFragmentManager, "datePicker")
        }
    }
}
