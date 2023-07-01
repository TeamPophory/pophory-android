package com.teampophory.pophory.config.initializer

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.teampophory.pophory.BuildConfig
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement) =
                    "Pophory://${element.fileName}:${element.lineNumber}#${element.methodName}"
            })
        } else {
            Timber.plant(object : Timber.Tree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    when (priority) {
                        Log.WARN, Log.ERROR -> t?.let {
                            val newThrowable = Throwable().initCause(t).apply {
                                stackTrace = Thread.currentThread().stackTrace
                            }
                            FirebaseCrashlytics.getInstance().recordException(newThrowable)
                        }
                        else -> {
                            FirebaseCrashlytics.getInstance().log("$tag | $message")
                        }
                    }
                }
            })
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(CrashlyticsInitializer::class.java)
    }
}