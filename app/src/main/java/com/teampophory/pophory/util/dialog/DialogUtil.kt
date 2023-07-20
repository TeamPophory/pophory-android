package com.teampophory.pophory.util.dialog

import androidx.annotation.DrawableRes
import androidx.fragment.app.FragmentManager

object DialogUtil {
    fun showOneButtonDialog(
        supportFragmentManager: FragmentManager,
        title: String,
        description: String,
        buttonText: String,
        @DrawableRes imageResId: Int
    ) {
        OneButtonCommonDialog.newInstance(
            title = title,
            description = description,
            buttonText = buttonText,
            imageResId = imageResId
        ).let {
            supportFragmentManager.beginTransaction().add(it, OneButtonCommonDialog.TAG)
        }.commitAllowingStateLoss()
    }

    fun hideOneButtonDialog(supportFragmentManager: FragmentManager) {
        supportFragmentManager.findFragmentByTag(OneButtonCommonDialog.TAG)?.let {
            (it as? OneButtonCommonDialog)?.dismissAllowingStateLoss()
        }
    }
}