package org.example.project.presentations.screen.tuition_payment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.TuitionUiModel
import org.example.project.presentations.components.TabRowView
import org.example.project.presentations.components.TopScreenBar
import org.example.project.presentations.screen.tuition_payment.TuitionStatus
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun TuitionPaymentContent(
    uiState: TuitionStatus,
    onBack: () -> Unit,
    onChangeTab: (Int) -> Unit,
    onViewDetailTuition: (TuitionUiModel) -> Unit,
    onDismissDialog: () -> Unit
) {
    val color = LocalExtendedColors.current
    val tabs = listOf("Thanh Toán" to null, "Lịch sử" to null)

    Scaffold(
        containerColor = color.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopScreenBar<String>(
                title = "Thanh toán học phí",
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

            TabRowView(
                tabs = tabs,
                selectedTab = uiState.selectedTab,
                onTabSelected = onChangeTab
            )

            Spacer(Modifier.height(20.dp))

            if (uiState.selectedTab == 0) {
                uiState.currentTuitionDetail?.let {
                    PaymentContent(
                        color,
                        tuitionDetail = it
                    )
                }
            } else {
                PaymentHistoryContent(
                    tuitionList = uiState.allTuition, color,
                    onViewDetailTuition = onViewDetailTuition
                )
            }
        }
    }

    if (uiState.isShowDetailTuitionCourseDialog){
        DetailTuitionCourseDialog(
            courses = uiState.selectedTuitionDetail?.items ?: emptyList(),
            onDismiss = onDismissDialog
        )
    }
}