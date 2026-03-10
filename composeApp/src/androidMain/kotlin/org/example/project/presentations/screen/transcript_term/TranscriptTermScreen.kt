package org.example.project.presentations.screen.transcript_term

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.components.TopScreenBar
import org.example.project.presentations.screen.transcript_term.components.SubjectResultCard
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun TranscriptTermScreen(
    viewModel: TranscriptTermViewModel,
    onBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopScreenBar(
                title = "Chi tiết ${uiState.semesterLabel}",
                onBack = onBack,
                justView = true,
                yearValue = uiState.academicYear
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 15.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(
                items = uiState.subjects,
                key = { subject -> subject.subjectCode },
                contentType = { "SubjectResultCard" }
            ) { subject ->
                SubjectResultCard(
                    subjectCode = subject.subjectCode,
                    subjectName = subject.subjectName,
                    score10 = subject.score10,
                    score4 = subject.score4,
                    letterGrade = subject.letterGrade
                )
            }
        }
    }
}