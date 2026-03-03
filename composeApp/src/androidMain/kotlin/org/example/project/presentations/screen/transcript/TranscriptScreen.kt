package org.example.project.presentations.screen.transcript

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.presentations.components.AppTopBar
import org.example.project.presentations.screen.transcript.components.GpaCard
import org.example.project.presentations.screen.transcript.components.TranscriptPerTerm
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun TranscriptScreen(
    onOpenNotificationScreen: () -> Unit = {},
    onOpenTranscriptTerm: () -> Unit = {},
) {
    val gpa = 2.4
    val credit = 36

    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        topBar = {
            AppTopBar(
                iconRes = R.drawable.icon_transcript,
                title = "Bảng điểm",
                onOpenNotificationScreen = onOpenNotificationScreen
            )
        }
    ) {
        LazyColumn (
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(horizontal = 20.dp),
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    GpaCard(
                        gpa = gpa,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }

            item {
                Text(
                    text = "Điểm học kỳ",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 10.dp, top = 20.dp)
                )
            }

            items(10){
                TranscriptPerTerm(
                    onOpenTranscriptTerm = onOpenTranscriptTerm
                )
                TranscriptPerTerm(
                    onOpenTranscriptTerm = onOpenTranscriptTerm
                )
                TranscriptPerTerm(
                    onOpenTranscriptTerm = onOpenTranscriptTerm
                )
            }
        }
    }
}