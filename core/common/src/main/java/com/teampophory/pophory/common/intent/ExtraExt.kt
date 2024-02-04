package com.teampophory.pophory.common.intent

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Parcelable
import timber.log.Timber
import java.io.Serializable
import kotlin.properties.ReadOnlyProperty

fun intExtra(defaultValue: Int = -1) = ReadOnlyProperty<Activity, Int> { thisRef, property ->
    thisRef.intent.extras?.getInt(
        property.name,
        defaultValue,
    ) ?: defaultValue
}

fun longExtra(defaultValue: Long = -1) = ReadOnlyProperty<Activity, Long> { thisRef, property ->
    thisRef.intent.extras?.getLong(
        property.name,
        defaultValue,
    ) ?: defaultValue
}

fun boolExtra(defaultValue: Boolean = false) =
    ReadOnlyProperty<Activity, Boolean> { thisRef, property ->
        thisRef.intent.extras?.getBoolean(
            property.name,
            defaultValue,
        ) ?: defaultValue
    }

fun stringExtra(defaultValue: String? = null) =
    ReadOnlyProperty<Activity, String?> { thisRef, property ->
        if (defaultValue == null) {
            thisRef.intent.extras?.getString(property.name)
        } else {
            thisRef.intent.extras?.getString(property.name, defaultValue)
        }
    }

fun <P : Parcelable> parcelableExtra(defaultValue: P? = null) =
    ReadOnlyProperty<Activity, P?> { thisRef, property ->
        thisRef.intent.extras?.getParcelable(property.name) ?: defaultValue
    }

inline fun <reified S : Serializable> serializableExtra(defaultValue: S? = null) =
    ReadOnlyProperty<Activity, S?> { thisRef, property ->
        thisRef.intent.serializableExtra(property.name) ?: defaultValue
    }

inline fun <reified T : Serializable?> Intent.serializableExtra(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(key, T::class.java)
    } else {
        getSerializableExtra(key) as? T
    }
}

inline fun <reified T : Parcelable> Intent.getCompatibleParcelableExtra(key: String): T? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getParcelableExtra(key, T::class.java)
        } else {
            getParcelableExtra(key)
        }
    } catch (e: ClassCastException) {
        Timber.e("IntentExtensions", "Failed to cast Parcelable object", e)
        null
    }
}
