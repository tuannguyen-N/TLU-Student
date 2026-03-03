package org.example.project.presentations.screen.transcript_term

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.presentations.components.TopScreenBar
import org.example.project.presentations.screen.transcript_term.components.SubjectResultCard
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun TranscriptTermScreen(
    onBack: () -> Unit = {}
) {
    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        topBar = {
            TopScreenBar(
                title = "Chi tiết học kỳ 1",
                onBack = onBack
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 20.dp),
        ) {
            items(10) {
                SubjectResultCard(
                    modifier = Modifier.padding(top = 15.dp)
                )
            }
        }
    }
}