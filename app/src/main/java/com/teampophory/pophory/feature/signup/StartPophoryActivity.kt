package com.teampophory.pophory.feature.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityStartPophoryBinding
import com.teampophory.pophory.feature.home.HomeActivity

class StartPophoryActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityStartPophoryBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnStartPophory.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}
