package com.teampophory.pophory.config.initializer

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.google.android.gms.ads.MobileAds

class AdInitializer: Initializer<Unit> {
    override fun create(context: Context) {
        try {
            MobileAds.initialize(context) {}
        } catch (e: Throwable) {
            Log.e("AdInitializer", "MobileAds.initialize failure", e)
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
