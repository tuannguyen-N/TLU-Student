package org.example.project.presentations.screen.application_detail

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.components.LoadingView
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.components.TopCenterScreenBar
import org.example.project.presentations.screen.application_detail.components.ApplicationDetailContent
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun ApplicationDetailScreen(
    viewModel: ApplicationDetailViewModel,
    onBack: () -> Unit = {},
    onOpenUrl: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    StatusBarStyle(darkIcons = false)

    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopCenterScreenBar(
                onBack = onBack,
                title = "Chi tiết đơn từ"
            )
        }
    ) {
        if (uiState.isLoading) {
            LoadingView()
        } else if (uiState.error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = uiState.error ?: "Đã có lỗi xảy ra")
            }
        } else {
            uiState.data?.let { data ->
                ApplicationDetailContent(
                    data = data,
                    onOpenUrl = onOpenUrl,
                    modifier = Modifier.padding(it)
                )
            }
        }
    }
}