package com.example.iot_compose.ui.webview

import android.app.NotificationManager
import android.content.Context
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.getSystemService
import com.example.iot_compose.url.URL.StreamUrl

@Composable
fun WebStream() {
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(start = 5.dp, end = 5.dp, bottom = 100.dp, top = 80.dp)),
        factory = {
            WebView(it).apply {

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
                setInitialScale(150)
                webViewClient = WebViewClient()
                loadUrl(StreamUrl)
            }
        }, update = {
            it.loadUrl(StreamUrl)
        })
}