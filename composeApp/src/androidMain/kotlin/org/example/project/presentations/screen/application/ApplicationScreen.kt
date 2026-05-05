package org.example.project.presentations.screen.application

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.domain.model.SubmitState
import org.example.project.presentations.components.LoadingView
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.components.TopCenterScreenBar
import org.example.project.presentations.dialog.FailureDialog
import org.example.project.presentations.dialog.SuccessDialog
import org.example.project.presentations.screen.application.components.ApplicationContent
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun ApplicationScreen(
    viewModel: ApplicationViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

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
        ApplicationContent(
            modifier = Modifier.padding(paddingValues),
            uiState = uiState,
            onApplicationChange = viewModel::onApplicationChange,
            onSubjectExpandedChange = viewModel::onSubjectExpandedChange,
            onAddFile = viewModel::onAddFile,
            onRemoveFile = viewModel::onRemoveFile,
            onSubmit = {
                val uri = uiState.attachedFile?.toUri()
                val fileBytes = context.contentResolver
                    .openInputStream(uri!!)
                    ?.use { it.readBytes() }
                    ?: return@ApplicationContent
                viewModel.onSubmit(fileBytes)
            }
        )
    }

    when (uiState.submitState) {
        is SubmitState.Error -> FailureDialog(onDismiss = viewModel::onDismiss)
        is SubmitState.Idle -> Unit
        is SubmitState.Loading -> LoadingView()
        is SubmitState.Success ->
            SuccessDialog(
                onDismiss = {
                    viewModel.onDismiss()
                    onBack()
                }
            )
    }
}