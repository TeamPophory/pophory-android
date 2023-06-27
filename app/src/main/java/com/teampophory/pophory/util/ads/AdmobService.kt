package com.teampophory.pophory.util.ads

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resumeWithException

class AdmobService @Inject constructor(
    @ActivityContext private val context: Context
) {
    suspend fun getNativeAd() = suspendCancellableCoroutine {
        val adLoader = AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
            .forNativeAd { ad: NativeAd? ->
                it.resumeWith(Result.success(ad))
            }.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    it.resumeWithException(Throwable(adError.toString()))
                }
            }).build()

        adLoader.loadAd(AdRequest.Builder().build())
    }
}
