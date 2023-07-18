package com.teampophory.pophory.feature.story

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityStoryBinding

class StoryActivity : AppCompatActivity() {
    private val binding: ActivityStoryBinding by viewBinding(ActivityStoryBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
