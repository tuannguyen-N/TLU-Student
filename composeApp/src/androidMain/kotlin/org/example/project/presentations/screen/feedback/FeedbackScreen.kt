package org.example.project.presentations.screen.feedback

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.domain.model.SubmitState
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.components.TopCenterScreenBar
import org.example.project.presentations.dialog.FailureDialog
import org.example.project.presentations.dialog.SuccessDialog
import org.example.project.presentations.screen.feedback.components.FeedbackFormContent
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun FeedbackScreen(
    viewModel: FeedbackViewModel,
    onBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StatusBarStyle(darkIcons = false)

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
        FeedbackFormContent(
            modifier = Modifier
                .padding(paddingValues)
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
    }

    when (uiState.submitState) {
        is SubmitState.Error -> FailureDialog(
            message = (uiState.submitState as SubmitState.Error).message,
            onDismiss = viewModel::onDismiss
        )

        is SubmitState.Idle -> Unit
        is SubmitState.Loading -> Unit
        is SubmitState.Success -> SuccessDialog(onDismiss = { onBack() })
    }
}