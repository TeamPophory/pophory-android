package com.teampophory.pophory.common.view

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun DialogFragment.showAllowingStateLoss(supportFragmentManager: FragmentManager, tag: String = "") {
    supportFragmentManager.beginTransaction().add(this, tag).commitAllowingStateLoss()
}
