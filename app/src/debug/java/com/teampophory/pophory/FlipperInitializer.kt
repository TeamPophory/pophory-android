package com.teampophory.pophory

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import okhttp3.OkHttpClient

object FlipperInitializer {
    private val networkFlipperPlugin by lazy { NetworkFlipperPlugin() }
    private val flipper: FlipperOkhttpInterceptor? by lazy {
        AndroidFlipperClient.getInstanceIfInitialized()
            ?.getPlugin<NetworkFlipperPlugin>(NetworkFlipperPlugin.ID)
            ?.let { plugin ->
                FlipperOkhttpInterceptor(plugin, true)
            }
    }

    fun execute(context: Context) {
        SoLoader.init(context, false)

        AndroidFlipperClient.getInstance(context).apply {
            addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
            addPlugin(networkFlipperPlugin)
            addPlugin(CrashReporterPlugin.getInstance())
        }.start()
    }

    fun initOkHttpClient(okHttpClientBuilder: OkHttpClient.Builder) {
        flipper?.let {
            okHttpClientBuilder.addNetworkInterceptor(it)
        }
    }
}
