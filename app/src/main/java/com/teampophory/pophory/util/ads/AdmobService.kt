package com.teampophory.pophory.util.ads

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.teampophory.pophory.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ActivityContext


class AdmobService @AssistedInject constructor(
    @ActivityContext private val context: Context,
    @Assisted private val frameLayout: FrameLayout
) {
    fun getNativeAd() {
        val adLoader = AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
            .forNativeAd { ad: NativeAd? ->
                ad?.let { nativeAd ->
                    frameLayout.apply {
                        removeAllViews()
                        addView(nativeAd.toNativeAdView())
                    }
                }
            }.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.e("ADS_ERROR", adError.toString())
                }
            }).build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun NativeAd.toNativeAdView(): NativeAdView? {
        return try {
            LayoutInflater.from(context)
                .inflate(R.layout.admob_native_default, null)
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

@AssistedFactory
interface AdmobServiceFactory {
    fun create(frameLayout: FrameLayout): AdmobService
}
