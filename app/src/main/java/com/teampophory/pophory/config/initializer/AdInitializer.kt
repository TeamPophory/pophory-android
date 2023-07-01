package com.teampophory.pophory.config.initializer

import android.content.Context
import androidx.startup.Initializer
import com.google.android.gms.ads.MobileAds
import timber.log.Timber

class AdInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        try {
            MobileAds.initialize(context) {}
        } catch (e: Throwable) {
            Timber.e("MobileAds.initialize failure", e)
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(TimberInitializer::class.java)
    }
}
