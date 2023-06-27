package com.teampophory.pophory

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.teampophory.pophory.databinding.ActivityAdmobSampleBinding
import com.teampophory.pophory.util.ads.AdmobService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AdmobSampleActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAdmobSampleBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var admobService: AdmobService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.root.setOnClickListener {
            lifecycleScope.launch {
                admobService.getNativeAd()?.let { nativeAd ->
                    binding.adContainer.apply {
                        removeAllViews()
                        addView(nativeAd.toNativeAdView())
                    }
                }
            }
        }
    }

    private fun NativeAd.toNativeAdView(): NativeAdView? {
        return try {
            layoutInflater.inflate(R.layout.admob_native_default, null)
                .let { nativeAdView ->
                    nativeAdView as? NativeAdView
                }?.apply {
                    setViewContent(this, this@toNativeAdView)
                    setNativeAd(this@toNativeAdView)
                }
        } catch (e: Exception) {
            Log.e("ADS_ERROR", e.toString())
            null
        }
    }

    private fun setViewContent(nativeAdView: NativeAdView, nativeAd: NativeAd) {
        nativeAdView.apply {
            iconView = findViewById(R.id.ad_app_icon)
            headlineView = findViewById(R.id.ad_headline)
            bodyView = findViewById(R.id.ad_body)
            callToActionView = findViewById(R.id.ad_call_to_action)
            (headlineView as? TextView)?.text = nativeAd.headline
            (bodyView as? TextView)?.text = nativeAd.body
            (callToActionView as? Button)?.text = nativeAd.callToAction
            (iconView as? ImageView)?.load(nativeAd.icon?.uri)
        }
    }
}
