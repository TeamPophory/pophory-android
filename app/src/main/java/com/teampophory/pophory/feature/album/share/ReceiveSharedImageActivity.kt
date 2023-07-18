package com.teampophory.pophory.feature.album.share

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityReceiveSharedImageBinding

class ReceiveSharedImageActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityReceiveSharedImageBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
