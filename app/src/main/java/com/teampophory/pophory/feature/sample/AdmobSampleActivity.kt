package com.teampophory.pophory.feature.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.teampophory.pophory.config.ad.AdmobNativeAdFactory
import com.teampophory.pophory.config.ad.AdmobRewardedAdFactory
import com.teampophory.pophory.databinding.ActivityAdmobSampleBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@Deprecated("This is a sample activity for AdmobService. It will be removed soon.")
class AdmobSampleActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAdmobSampleBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var admobNativeAdFactory: AdmobNativeAdFactory

    @Inject
    lateinit var admobRewardedAdFactory: AdmobRewardedAdFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val rewardedAdService = admobRewardedAdFactory.create(
            adUnitId = "ca-app-pub-3940256099942544/5224354917",
        )
        rewardedAdService.loadAd()
        val service = admobNativeAdFactory.create(
            adUnitId = "ca-app-pub-3940256099942544/2247696110",
            adContainer = binding.adContainer,
        )

        binding.root.setOnClickListener {
            service.getNativeAd()
        }
    }
}
