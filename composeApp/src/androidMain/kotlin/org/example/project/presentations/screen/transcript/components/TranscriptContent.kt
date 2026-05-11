package org.example.project.presentations.screen.transcript.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.domain.model.SemesterUiModel
import org.example.project.domain.model.TranscriptUiModel
import org.example.project.presentations.components.AppTopBar
import org.example.project.presentations.components.LoadingView
import org.example.project.presentations.screen.transcript.TranscriptState
import org.example.project.presentations.theme.LocalExtendedColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranscriptContent(
    onOpenNotificationScreen: () -> Unit,
    uiState: TranscriptState,
    transcriptUiModel: TranscriptUiModel?,
    onOpenTranscriptTerm: (SemesterUiModel) -> Unit,
    onOpenChat: () -> Unit,
    onRefresh: () -> Unit,
    onOpenGpaTracker: () -> Unit
) {
    val pullRefreshState = rememberPullToRefreshState()
    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            AppTopBar(
                iconRes = R.drawable.icon_transcript,
                title = "Bảng điểm",
                onOpenNotificationScreen = onOpenNotificationScreen,
                onOpenChat = onOpenChat,
                isNotificationBadgeVisible = !uiState.isAllNotificationsRead
            )
        }
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = onRefresh,
            state = pullRefreshState,
            modifier = Modifier.padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                contentPadding = PaddingValues(bottom = 60.dp)
            ) {
                item(key = "gpa_card", contentType = "GpaCard") {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        GpaCard(
                            gpa = uiState.gpa,
                            credit = uiState.passedCredits,
                            totalCredits = uiState.totalCredits,
                            onOpenGpaTracker = onOpenGpaTracker,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }

                when {
                    uiState.isLoading -> {
                        item(key = "loading", contentType = "Loading") {
                            LoadingView()
                        }
                    }

                    uiState.error != null -> {
                        item(key = "error", contentType = "Error") {
                            Text(
                                text = uiState.error,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }
                    }

                    transcriptUiModel != null -> {
                        item(key = "semester_header", contentType = "Header") {
                            Text(
                                text = "Điểm học kỳ",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 10.dp, top = 20.dp)
                            )
                        }

                        items(
                            items = transcriptUiModel.academicYearGroups,
                            key = { group -> group.academicYear },
                            contentType = { "AcademicYearGroup" }
                        ) { group ->
                            TranscriptPerTerm(
                                academicYear = group.academicYear,
                                semesters = group.semesters,
                                onOpenTranscriptTerm = onOpenTranscriptTerm
                            )
                        }
                    }
                }
            }
        }
    }
}