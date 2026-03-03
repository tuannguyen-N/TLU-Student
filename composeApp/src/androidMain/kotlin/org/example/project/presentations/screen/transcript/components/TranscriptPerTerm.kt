package org.example.project.presentations.screen.transcript.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun TranscriptPerTerm(
    modifier: Modifier = Modifier,
    onOpenTranscriptTerm: () -> Unit = {},
    academicYear: String = "2025-2026"
){
    Column(
        modifier = modifier.padding(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        SubjectCard(
            onOpenTranscriptTerm = onOpenTranscriptTerm
        )

        SubjectCard(
            onOpenTranscriptTerm = onOpenTranscriptTerm
        )

        SubjectCard(
            onOpenTranscriptTerm = onOpenTranscriptTerm
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 0.5.dp,
                color = LocalExtendedColors.current.gray
            )

            Text(
                text = academicYear,
                color = LocalExtendedColors.current.gray,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 0.5.dp,
                color = LocalExtendedColors.current.gray
            )
        }
    }
}