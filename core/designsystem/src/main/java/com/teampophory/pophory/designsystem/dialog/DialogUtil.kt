package com.teampophory.pophory.designsystem.dialog

import androidx.annotation.DrawableRes
import androidx.fragment.app.FragmentManager

object DialogUtil {
    fun showOneButtonDialog(
        supportFragmentManager: FragmentManager,
        title: String,
        description: String,
        buttonText: String,
        @DrawableRes imageResId: Int,
        buttonClickListener: (() -> Unit)? = null
    ) {
        val dialog = OneButtonCommonDialog.newInstance(
            title = title,
            description = description,
            buttonText = buttonText,
            imageResId = imageResId
        )

        buttonClickListener?.let { listener ->
            dialog.setButtonClickListener(listener)
        }

        dialog.show(supportFragmentManager, OneButtonCommonDialog.TAG)
    }

    fun showForceUpdateDialog(
        supportFragmentManager: FragmentManager
    ) {
        val dialog = ForceUpdateDialog.newInstance()
        dialog.show(supportFragmentManager, ForceUpdateDialog.TAG)
    }

    fun hideOneButtonDialog(supportFragmentManager: FragmentManager) {
        supportFragmentManager.findFragmentByTag(OneButtonCommonDialog.TAG)?.let {
            (it as? OneButtonCommonDialog)?.dismissAllowingStateLoss()
        }
    }
}
