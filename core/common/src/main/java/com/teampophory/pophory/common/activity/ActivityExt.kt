package com.teampophory.pophory.common.activity

import androidx.appcompat.app.AppCompatActivity
import com.teampophory.pophory.common.view.LoadingProgressIndicator

fun AppCompatActivity.showLoading() {
    supportFragmentManager.beginTransaction()
        .add(LoadingProgressIndicator.newInstance(), LoadingProgressIndicator.TAG)
        .commitAllowingStateLoss()
}

fun AppCompatActivity.hideLoading() {
    supportFragmentManager.findFragmentByTag(LoadingProgressIndicator.TAG)?.let {
        supportFragmentManager.beginTransaction()
            .remove(it)
            .commitAllowingStateLoss()
    }
}