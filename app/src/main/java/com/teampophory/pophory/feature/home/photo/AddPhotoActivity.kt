package com.teampophory.pophory.feature.home.photo

import android.os.Bundle
import com.teampophory.pophory.R
import com.teampophory.pophory.common.activity.BindingActivity
import com.teampophory.pophory.databinding.ActivityAddPhotoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPhotoActivity : BindingActivity<ActivityAddPhotoBinding>(R.layout.activity_add_photo) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
