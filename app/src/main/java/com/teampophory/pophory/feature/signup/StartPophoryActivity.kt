package com.teampophory.pophory.feature.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teampophory.pophory.R
import com.teampophory.pophory.databinding.ActivityStartPophoryBinding
import com.teampophory.pophory.feature.home.HomeActivity

class StartPophoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityStartPophoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartPophoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartPophory.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}