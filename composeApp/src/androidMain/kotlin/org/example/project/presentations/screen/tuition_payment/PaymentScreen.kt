package org.example.project.presentations.screen.tuition_payment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.PaymentStatus
import org.example.project.domain.model.PaymentType
import org.example.project.domain.model.TuitionDetailUiModel
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.components.TopScreenBar
import org.example.project.presentations.screen.tuition_payment.components.DetailTuitionCourse
import org.example.project.presentations.screen.tuition_payment.components.PaymentMethodList
import org.example.project.presentations.screen.tuition_payment.components.TuitionCard
import org.example.project.presentations.theme.LocalExtendedColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    tuitionDetail: TuitionDetailUiModel,
    selectedPaymentType: PaymentType,
    isRefreshing: Boolean,
    onBack: () -> Unit,
    onSelectPaymentType: (PaymentType) -> Unit,
    onPay: (Int, PaymentType) -> Unit,
    onRefresh: () -> Unit
) {
    val color = LocalExtendedColors.current
    val pullRefreshState = rememberPullToRefreshState()

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
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            state = pullRefreshState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 25.dp)
            ) {
                item { Spacer(Modifier.height(20.dp)) }

                item {
                    TuitionCard(
                        color = color,
                        semester = tuitionDetail.semester,
                        totalAmount = if (tuitionDetail.status == PaymentStatus.UNPAID) tuitionDetail.totalAmount else "0",
                        deadline = tuitionDetail.dueDate,
                        status = tuitionDetail.status
                    )
                }

                item { Spacer(Modifier.height(20.dp)) }

                item { DetailTuitionCourse(color = color, items = tuitionDetail.items) }

                if (tuitionDetail.status == PaymentStatus.UNPAID) {
                    item { Spacer(Modifier.height(20.dp)) }

                    item {
                        PaymentMethodList(
                            selectedType = selectedPaymentType,
                            onSelect = onSelectPaymentType
                        )
                    }

                    item { Spacer(Modifier.height(20.dp)) }

                    item {
                        ButtonView(
                            text = "Thanh toán ngay",
                            enabled = true,
                            textColorRes = color.white,
                            backgroundColorRes = color.red,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.padding(horizontal = 20.dp),
                            endIconRes = Icons.AutoMirrored.Filled.ArrowForward,
                            onClick = {
                                onPay(
                                    tuitionDetail.tuitionId,
                                    selectedPaymentType
                                )
                            }
                        )
                    }
                }

                item { Spacer(Modifier.height(20.dp)) }
            }
        }
    }
}