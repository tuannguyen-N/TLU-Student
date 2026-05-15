package org.example.project.presentations.screen.tuition_payment

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.domain.model.TuitionDetailUiModel
import org.example.project.presentations.components.LoadingView
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.components.WebViewScreen
import org.example.project.presentations.screen.tuition_payment.components.TuitionPaymentContent

@Composable
fun TuitionPaymentScreen(
    viewModel: TuitionPaymentViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StatusBarStyle(darkIcons = true)

    when {
        uiState.isProcessingPayment -> {
            LoadingView()
        }
        uiState.paymentUrl != null -> {
            val context = LocalContext.current

            LaunchedEffect(uiState.paymentUrl) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uiState.paymentUrl))
                context.startActivity(intent)
                viewModel.onPaymentUrlHandled()
            }
        }
        uiState.isInPaymentScreen && uiState.selectedTuitionDetail != null -> {
            val detail: TuitionDetailUiModel = uiState.selectedTuitionDetail!!
            PaymentScreen(
                tuitionDetail = detail,
                selectedPaymentType = uiState.selectedPaymentType,
                isRefreshing = uiState.isRefreshingDetail,
                onBack = viewModel::onBackFromPayment,
                onSelectPaymentType = viewModel::onSelectPaymentType,
                onPay = viewModel::onPay,
                onRefresh = viewModel::onRefreshDetail
            )
        }
        else -> {
            TuitionPaymentContent(
                uiState = uiState,
                onBack = onBack,
                onNavigateToPayment = viewModel::onNavigateToPayment,
                onDismissDialog = viewModel::onDismissDialog,
                onRefresh = viewModel::onRefreshData
            )
        }
    }
}