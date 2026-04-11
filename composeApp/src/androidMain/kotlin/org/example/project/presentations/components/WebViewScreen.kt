package org.example.project.presentations.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewScreen(
    url: String,
    modifier: Modifier = Modifier
) {
    StatusBarStyle(true)

    AndroidView(
        modifier = modifier.fillMaxSize().padding(top = 20.dp),
        factory = { context ->
            android.webkit.WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = android.webkit.WebViewClient()
                loadUrl(url)
            }
        }
    )
}