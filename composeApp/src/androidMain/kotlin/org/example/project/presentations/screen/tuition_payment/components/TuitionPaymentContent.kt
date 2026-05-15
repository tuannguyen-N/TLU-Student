package org.example.project.presentations.screen.tuition_payment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.TuitionUiModel
import org.example.project.presentations.components.TopScreenBar
import org.example.project.presentations.screen.tuition_payment.TuitionStatus
import org.example.project.presentations.theme.LocalExtendedColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TuitionPaymentContent(
    uiState: TuitionStatus,
    onBack: () -> Unit,
    onNavigateToPayment: (TuitionUiModel) -> Unit,
    onDismissDialog: () -> Unit,
    onRefresh: () -> Unit
) {
    val color = LocalExtendedColors.current
    val pullRefreshState = rememberPullToRefreshState()

    Scaffold(
        containerColor = color.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopScreenBar<String>(
                title = "Tài chính sinh viên",
                enableListItem = true,
                onBack = onBack,
                backgroundColor = color.white,
                contentColor = color.blackBackground
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 25.dp)
                .padding(top = 18.dp)
        ) {
            PullToRefreshBox(
                isRefreshing = uiState.isRefreshing,
                onRefresh = onRefresh,
                state = pullRefreshState,
                modifier = Modifier.fillMaxSize()
            ) {
                PaymentHistoryContent(
                    tuitionList = uiState.allTuition,
                    color = color,
                    onNavigateToPayment = onNavigateToPayment
                )
            }
        }
    }

    if (uiState.isShowDetailTuitionCourseDialog) {
        DetailTuitionCourseDialog(
            courses = uiState.selectedTuitionDetail?.items ?: emptyList(),
            onDismiss = onDismissDialog
        )
    }
}