package com.teampophory.pophory.common.context

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.snackBar(anchorView: View, message: () -> String) {
    Snackbar.make(anchorView, message(), Snackbar.LENGTH_SHORT).show()
}

fun Context.stringOf(@StringRes resId: Int) = getString(resId)

fun Context.colorOf(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

fun Context.drawableOf(@DrawableRes resId: Int) = ContextCompat.getDrawable(this, resId)

fun Context.dialogWidthPercent(dialog: Dialog?, percent: Double = 0.8) {
    val deviceSize = getDeviceSize()
    dialog?.window?.run {
        val params = attributes
        params.width = (deviceSize[0] * percent).toInt()
        attributes = params
    }
}

fun Context.getDeviceSize(): IntArray {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = windowManager.currentWindowMetrics
        val windowInsets = windowMetrics.windowInsets

        val insets = windowInsets.getInsetsIgnoringVisibility(
            WindowInsets.Type.navigationBars() or WindowInsets.Type.displayCutout()
        )
        val insetsWidth = insets.right + insets.left
        val insetsHeight = insets.top + insets.bottom

        val bounds = windowMetrics.bounds

        return intArrayOf(bounds.width() - insetsWidth, bounds.height() - insetsHeight)
    } else {
        val display = windowManager.defaultDisplay
        val size = Point()

        display?.getSize(size)

        return intArrayOf(size.x, size.y)
    }
}