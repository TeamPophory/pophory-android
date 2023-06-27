package com.teampophory.pophory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teampophory.pophory.databinding.ActivityAdmobSampleBinding
import com.teampophory.pophory.util.ads.AdmobService

class AdmobSampleActivity : AppCompatActivity() {
        private val binding by lazy {
            ActivityAdmobSampleBinding.inflate(layoutInflater)
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.root.setOnClickListener {
            AdmobService().getNativeAd(this, binding.adContainer)
        }
    }
}