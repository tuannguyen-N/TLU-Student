package org.example.project.presentations.screen.feedback_detail

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.presentations.components.TopCenterScreenBar
import org.example.project.presentations.screen.feedback_detail.components.FeedbackDetailContent
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun FeedbackDetailScreen(
    onBack: () -> Unit = {}
) {
    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopCenterScreenBar(
                onBack = onBack,
                title = "Chi tiết phản hồi"
            )
        }
    ) {
        FeedbackDetailContent(
            modifier = Modifier.padding(it)
        )
    }
}