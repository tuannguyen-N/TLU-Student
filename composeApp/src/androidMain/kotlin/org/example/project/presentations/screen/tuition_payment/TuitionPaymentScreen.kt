package org.example.project.presentations.screen.tuition_payment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.screen.tuition_payment.components.TuitionPaymentContent

@Composable
fun TuitionPaymentScreen(
    viewModel: TuitionPaymentViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StatusBarStyle(darkIcons = true)

    TuitionPaymentContent(
        uiState = uiState,
        onBack = onBack,
        onChangeTab = viewModel::onChangeTab,
        onViewDetailTuition = viewModel::onViewDetailTuition,
        onDismissDialog = viewModel::onDismissDialog,
    )
}