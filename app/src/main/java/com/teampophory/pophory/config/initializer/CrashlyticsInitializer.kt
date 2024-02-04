package com.teampophory.pophory.config.initializer

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.crashlytics.FirebaseCrashlytics

class CrashlyticsInitializer : Initializer<FirebaseCrashlytics> {
    override fun create(context: Context): FirebaseCrashlytics {
        return FirebaseCrashlytics.getInstance()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
