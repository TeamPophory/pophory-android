package com.teampophory.pophory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teampophory.pophory.databinding.ActivityAdmobSampleBinding

class AdmobSampleActivity : AppCompatActivity() {
        private val binding by lazy {
            ActivityAdmobSampleBinding.inflate(layoutInflater)
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admob_sample)

    }
}