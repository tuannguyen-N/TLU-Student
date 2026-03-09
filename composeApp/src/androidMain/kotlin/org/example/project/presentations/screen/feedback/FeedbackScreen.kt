package org.example.project.presentations.screen.feedback

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.presentations.components.TopCenterScreenBar
import org.example.project.presentations.screen.feedback.components.FeedbackFormContent
import org.example.project.presentations.screen.feedback.components.FeedbackHistoryContent
import org.example.project.presentations.components.TabRowView
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun FeedbackScreen(
    viewModel: FeedbackViewModel = viewModel(),
    onBack: () -> Unit ={}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val tabs = listOf("Tạo phản hồi", "Lịch sử")

    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopCenterScreenBar(
                onBack = onBack,
                title = "Phản hồi"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TabRowView(
                tabs = tabs,
                selectedTab = uiState.selectedTab,
                onTabSelected = viewModel::onTabSelected
            )

            if (uiState.selectedTab == 0) {
                FeedbackFormContent(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    uiState = uiState,
                    onTitleChange = viewModel::onTitleChange,
                    onSubjectChange = viewModel::onSubjectChange,
                    onContentChange = viewModel::onContentChange,
                    onSubjectExpandedChange = viewModel::onSubjectExpandedChange,
                    onSubmit = viewModel::onSubmit,
                    onRemoveImage = viewModel::onRemoveImage,
                    onAddImage = viewModel::onAddImage
                )
            } else {
                FeedbackHistoryContent(
                    onCreateFeedback = { viewModel.onTabSelected(0) },
                    onViewDetail = {
                        // TODO:
                    }
                )
            }
        }
    }
}