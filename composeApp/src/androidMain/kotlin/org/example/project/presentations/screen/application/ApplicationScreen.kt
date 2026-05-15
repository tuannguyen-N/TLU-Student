package org.example.project.presentations.screen.application

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.domain.model.SubmitState
import org.example.project.presentations.components.LoadingView
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.components.TabRowView
import org.example.project.presentations.components.TopCenterScreenBar
import org.example.project.presentations.dialog.FailureDialog
import org.example.project.presentations.dialog.SuccessDialog
import org.example.project.presentations.screen.application.components.ApplicationContent
import org.example.project.presentations.screen.application.components.ApplicationHistoryContent
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun ApplicationScreen(
    viewModel: ApplicationViewModel,
    onBack: () -> Unit,
    onNavigateToDetail: (Int) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val tabs = listOf(
        "Tạo đơn từ" to Icons.Filled.Edit,
        "Lịch sử" to Icons.Filled.History
    )

    StatusBarStyle(darkIcons = false)

    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopCenterScreenBar(
                onBack = onBack,
                title = "Đơn từ sinh viên"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            TabRowView(
                tabs = tabs,
                selectedTab = uiState.selectedTab,
                onTabSelected = viewModel::onTabSelected,
                modifier = Modifier.padding(top = 12.dp)
            )
            if (uiState.selectedTab == 0) {
                ApplicationContent(
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
                    },
                    onContentChange = viewModel::onContentChange
                )
            } else {
                ApplicationHistoryContent(
                    applications = uiState.applicationHistory,
                    onCreateFeedback = { viewModel.onTabSelected(0) },
                    onViewDetail = { idString ->
                        idString.toIntOrNull()?.let { onNavigateToDetail(it) }
                    }
                )
            }
        }
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