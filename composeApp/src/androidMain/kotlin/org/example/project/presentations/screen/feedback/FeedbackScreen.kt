package org.example.project.presentations.screen.feedback

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.components.TopCenterScreenBar
import org.example.project.presentations.screen.feedback.components.FeedbackFormContent
import org.example.project.presentations.screen.feedback.components.FeedbackHistoryContent
import org.example.project.presentations.components.TabRowView
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun FeedbackScreen(
    viewModel: FeedbackViewModel,
    onBack: () -> Unit ={}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val tabs = listOf(
        "Tạo phản hồi" to Icons.Filled.Edit,
        "Lịch sử" to Icons.Filled.History
    )

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
                onTabSelected = viewModel::onTabSelected,
                modifier = Modifier.padding(top = 12.dp)
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