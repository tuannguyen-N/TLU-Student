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
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.SemesterUiModel
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun TranscriptPerTerm(
    modifier: Modifier = Modifier,
    academicYear: String,
    semesters: List<SemesterUiModel>,
    onOpenTranscriptTerm: (SemesterUiModel) -> Unit = {},
) {
    Column(
        modifier = modifier.padding(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        semesters.forEachIndexed { _, semester ->
            SubjectCard(
                onOpenTranscriptTerm = ({ onOpenTranscriptTerm(semester) }),
                termNumber = semester.semesterLabel.last().toString(), // TODO: update when add temp semester
                subjects = semester.subjects.map { it.subjectName },
                gpa = semester.semesterGpa,
                credits = semester.creditsPassed,
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
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