package org.example.project.presentations.screen.tuition_payment

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.domain.model.TuitionDetailUiModel
import org.example.project.presentations.components.LoadingView
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.dialog.FailureDialog
import org.example.project.presentations.dialog.SuccessDialog
import org.example.project.presentations.screen.tuition_payment.components.TuitionPaymentContent
import org.example.project.presentations.utils.CollectWithLifecycle

@Composable
fun TuitionPaymentScreen(
    viewModel: TuitionPaymentViewModel, onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var successMessage by remember { mutableStateOf<String?>(null) }
    var failureMessage by remember { mutableStateOf<String?>(null) }

    viewModel.uiEvent.CollectWithLifecycle { event ->
        when (event) {
            is TuitionUiEvent.ShowDialogPaymentSuccess -> successMessage =
                "Bạn đã thanh toán, vui lòng đợi hệ thống xác nhận."

            is TuitionUiEvent.ShowDialogPaymentFailed -> failureMessage = "failure"
        }
    }

    successMessage?.let { message ->
        SuccessDialog(
            title = "Thành công!",
            message = message,
            onDismiss = {
                viewModel.onBackFromPayment()
                successMessage = null
            })
    }

    failureMessage?.let { message ->
        FailureDialog(
            title = "Thất bại",
            message = "Thanh toán thất bại, vui lòng thử lại sau!",
            onDismiss = { failureMessage = null })
    }

    StatusBarStyle(darkIcons = true)

    when {
        uiState.isLoading -> {
            LoadingView()
        }

        uiState.paymentUrl != null -> {
            val context = LocalContext.current

            LaunchedEffect(uiState.paymentUrl) {
                val intent = Intent(Intent.ACTION_VIEW, uiState.paymentUrl!!.toUri())
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