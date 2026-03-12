package org.example.project.presentations.screen.features

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.FeatureEditMode
import org.example.project.domain.model.FeatureUiModel
import org.example.project.presentations.components.TopCenterScreenBar
import org.example.project.presentations.dialog.ExitConfirmDialog
import org.example.project.presentations.screen.features.components.FeatureGrid
import org.example.project.presentations.screen.features.components.FeatureSectionHeader
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.CollectWithLifecycle

@Composable
fun FeaturesScreen(
    viewModel: FeaturesViewModel,
    onBack: () -> Unit = {},
    onNavigate:(FeatureUiModel) -> Unit = {},
) {
    val isEditing by viewModel.isEditing.collectAsState()
    val isExitDialog by viewModel.showExitDialog.collectAsState()
    val quickAccessList by viewModel.quickAccessList.collectAsState()
    val quickAccessTypes = remember(quickAccessList) { quickAccessList.map { it.type }.toSet() }

    viewModel.event.CollectWithLifecycle { event ->
        when (event) {
            is FeatureUiEvent.OnBack -> onBack()
        }
    }

    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopCenterScreenBar(
                onBack = {
                    viewModel.onBack()
                },
                title = "Tiện ích",
                enableActionButton = isEditing,
                onClickAction = {
                    viewModel.toggleEditMode()
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            FeatureSectionHeader(
                title = "Truy cập nhanh",
                showEditIcon = !isEditing,
                onChangeEditMode = { viewModel.toggleEditMode() }
            )
            Spacer(modifier = Modifier.height(12.dp))
            FeatureGrid(
                items = quickAccessList,
                isEditing = isEditing,
                editMode = FeatureEditMode.REMOVE,
                isQuickAccessFull = quickAccessTypes.size >= 4,
                isQuickAccessMin = quickAccessTypes.size <= 1,
                onClickFeature = { onNavigate(it) },
                onClickRemoveFromQuickAccess = { viewModel.removeFromQuickAccess(it) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            FeatureSectionHeader(title = "Tiện ích chung")
            Spacer(modifier = Modifier.height(12.dp))
            FeatureGrid(
                items = FeatureUiModel.getGeneralList().filter { it.type !in quickAccessTypes },
                isEditing = isEditing,
                editMode = FeatureEditMode.ADD,
                onClickFeature = { onNavigate(it) },
                onClickAddToQuickAccess = { viewModel.addToQuickAccess(it) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            FeatureSectionHeader(title = "Hỗ trợ")
            Spacer(modifier = Modifier.height(12.dp))
            FeatureGrid(
                items = FeatureUiModel.getSupportList(),
                isEditing = false,
                onClickFeature = { onNavigate(it) }
            )
        }
    }

    if(isExitDialog){
        ExitConfirmDialog(
            onDismiss = {viewModel.onDismissExitDialog()},
            onConfirm = {
                onBack()
            }
        )
    }
}