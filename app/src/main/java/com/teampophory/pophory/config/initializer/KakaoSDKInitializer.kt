package com.teampophory.pophory.config.initializer

import android.content.Context
import androidx.startup.Initializer
import com.kakao.sdk.common.KakaoSdk
import com.teampophory.pophory.BuildConfig

class KakaoSDKInitializer: Initializer<Unit> {
    override fun create(context: Context) {
        KakaoSdk.init(context, BuildConfig.KAKAO_API_KEY)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
