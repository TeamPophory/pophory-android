package com.teampophory.pophory.feature.setting.webview

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import com.teampophory.pophory.R
import com.teampophory.pophory.common.compose.DefaultPreview
import com.teampophory.pophory.common.compose.bottomBorder
import com.teampophory.pophory.design.PophoryTheme

private val webViewClient = AccompanistWebViewClient()

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(
    url: String,
    onBack: () -> Unit = {}
) {
    val title by remember(webViewClient.state) { mutableStateOf(webViewClient.state.pageTitle) }
    val state = rememberWebViewState(url)
    val webViewNavigator = rememberWebViewNavigator()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = title.orEmpty(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (webViewNavigator.canGoBack) {
                            webViewNavigator.navigateBack()
                        } else {
                            onBack()
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chevron_left),
                            contentDescription = "Back To Home"
                        )
                    }
                },
                modifier = Modifier.bottomBorder(Dp.Hairline, PophoryTheme.colors.onSurface30)
            )
        }
    ) {
        WebView(
            state = state,
            modifier = Modifier.padding(it),
            onCreated = { webview ->
                webview.settings.javaScriptEnabled = true
            },
            client = webViewClient
        )
        BackHandler {
            if (webViewNavigator.canGoBack) {
                webViewNavigator.navigateBack()
            } else {
                onBack()
            }
        }
    }
}

@DefaultPreview
@Composable
private fun WebViewScreenPreview() {
    PophoryTheme {
        WebViewScreen("https://www.google.com")
    }
}
