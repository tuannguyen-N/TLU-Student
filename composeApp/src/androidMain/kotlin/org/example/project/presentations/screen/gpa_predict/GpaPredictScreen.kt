package org.example.project.presentations.screen.gpa_predict

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.screen.gpa_predict.components.GpaPredictContent

@Composable
fun GpaPredictScreen(
    viewModel: GpaPredictViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StatusBarStyle(darkIcons = true)

    GpaPredictContent(
        uiState = uiState,
        onBack = onBack,
        onMidtermChange = viewModel::onMidtermChange,
        onFinalChange = viewModel::onFinalChange,
        onPredictGpa = viewModel::onPredictGpa,
        onResetData = viewModel::onResetData
    )
}