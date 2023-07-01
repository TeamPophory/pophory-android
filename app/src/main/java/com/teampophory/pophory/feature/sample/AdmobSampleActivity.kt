package com.teampophory.pophory.feature.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teampophory.pophory.databinding.ActivityAdmobSampleBinding
import com.teampophory.pophory.config.ad.AdmobServiceFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@Deprecated("This is a sample activity for AdmobService. It will be removed soon.")
class AdmobSampleActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAdmobSampleBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var admobServiceFactory: AdmobServiceFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val service = admobServiceFactory.create(
            adUnitId = "ca-app-pub-3940256099942544/2247696110",
            adContainer = binding.adContainer
        )

        binding.root.setOnClickListener {
            service.getNativeAd()
        }
    }
}
