package com.teampophory.pophory.common.intent

import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty

fun intArgs() = ReadOnlyProperty<Fragment, Int> { thisRef, property ->
    thisRef.requireArguments().getInt(property.name)
}

fun longArgs() = ReadOnlyProperty<Fragment, Long> { thisRef, property ->
    thisRef.requireArguments().getLong(property.name)
}

fun boolArgs() = ReadOnlyProperty<Fragment, Boolean> { thisRef, property ->
    thisRef.requireArguments().getBoolean(property.name)
}

fun stringArgs() = ReadOnlyProperty<Fragment, String> { thisRef, property ->
    thisRef.requireArguments().getString(property.name, "")
}

fun <P : Parcelable> parcelableExtra() = ReadOnlyProperty<Fragment, P?> { thisRef, property ->
    thisRef.requireArguments().getParcelable(property.name)
}
