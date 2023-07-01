package com.teampophory.pophory.config.ad

import android.content.Context
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
import timber.log.Timber

class AdmobService @AssistedInject constructor(
    @ActivityContext private val context: Context,
    @Assisted private val adUnitId: String,
    @Assisted private val adContainer: FrameLayout
) {
    fun getNativeAd() {
        val adLoader = AdLoader.Builder(context, adUnitId)
            .forNativeAd { ad: NativeAd? ->
                ad?.let { nativeAd ->
                    adContainer.apply {
                        removeAllViews()
                        addView(nativeAd.toNativeAdView(context))
                    }
                }
            }.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Timber.e(adError.toString())
                }
            }).build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun NativeAd.toNativeAdView(context: Context): NativeAdView? {
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
            Timber.e(e)
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
    fun create(adUnitId: String, adContainer: FrameLayout): AdmobService
}
