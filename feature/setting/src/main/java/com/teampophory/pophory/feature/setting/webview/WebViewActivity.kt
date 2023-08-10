package com.teampophory.pophory.feature.setting.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.teampophory.pophory.common.intent.stringExtra
import com.teampophory.pophory.designsystem.PophoryTheme

class WebViewActivity : AppCompatActivity() {
    private val url by stringExtra("")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PophoryTheme {
                WebViewScreen(url = url.orEmpty(), onBack = { finish() })
            }
        }
    }

    companion object {
        fun newIntent(context: Context, url: String) =
            Intent(context, WebViewActivity::class.java).apply {
                putExtra("url", url)
            }
    }
}
