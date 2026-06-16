package org.example.project.presentations.screen.tuition_payment.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.PaymentStatus
import org.example.project.domain.model.TuitionUiModel
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.components.LabelHeader
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun PaymentHistoryContent(
    tuitionList: List<TuitionUiModel>?,
    color: ExtendedColors = LocalExtendedColors.current,
    onNavigateToPayment: (TuitionUiModel) -> Unit
) {
    if (tuitionList.isNullOrEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                "Trống",
                color = color.gray,
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.bodyLarge,
                fontStyle = FontStyle.Italic
            )
        }
    } else {
        val grouped = tuitionList.groupBy { it.semesterName }

        LazyColumn {
            grouped.forEach { (semesterName, tuitions) ->
                item { LabelHeader(label = semesterName) }

                items(tuitions) { tuition ->
                    PaymentCard(
                        item = tuition,
                        color = color,
                        onClick = onNavigateToPayment,
                        onPayment = onNavigateToPayment
                    )
                }

                item { Spacer(Modifier.height(8.dp)) }
            }
        }
    }
}

@Composable
private fun PaymentCard(
    item: TuitionUiModel,
    color: ExtendedColors,
    onClick: (TuitionUiModel) -> Unit = {},
    onPayment: (TuitionUiModel) -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        onClick = { onClick(item) },
        shape = RoundedCornerShape(16.dp),
        color = color.white,
        tonalElevation = 1.dp,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusIcon(status = item.status, color = color)

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Thanh toán học phí",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            color = color.blackBackground
                        )
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "Hạn: ${item.dueDate}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = color.gray
                        )
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = item.totalAmount,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = color.blackBackground
                        )
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    StatusLabel(status = item.status, color = color)
                }
            }

            if (item.status == PaymentStatus.UNPAID) {
                Spacer(modifier = Modifier.height(12.dp))
                ButtonView(
                    text = "Thanh toán",
                    enabled = true,
                    textColorRes = color.white,
                    backgroundColorRes = color.red,
                    shape = RoundedCornerShape(12.dp),
                    onClick = { onPayment(item) },
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            if (item.status == PaymentStatus.PENDING) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(14.dp),
                        color = color.orange,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Giao dịch đang đợc xử lý....",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = color.orange,
                            fontStyle = FontStyle.Italic
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusIcon(status: PaymentStatus, color: ExtendedColors) {
    val (bgColor, iconColor, icon) = when (status) {
        PaymentStatus.PAID -> Triple(
            color.green.copy(alpha = 0.2f),
            color.green,
            Icons.Filled.Check
        )

        PaymentStatus.UNPAID -> Triple(
            color.red.copy(alpha = 0.2f),
            color.red,
            Icons.Filled.Close
        )

        PaymentStatus.PENDING -> Triple(
            color.orange.copy(alpha = 0.2f),
            color.orange,
            Icons.Filled.Refresh
        )

        PaymentStatus.OVERDUE -> Triple(
            color.orange.copy(alpha = 0.15f),
            color.orange,
            Icons.Filled.Warning
        )

        PaymentStatus.CANCELLED -> Triple(
            color.gray.copy(alpha = 0.2f),
            color.gray,
            Icons.Filled.Block
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(bgColor)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun StatusLabel(status: PaymentStatus, color: ExtendedColors) {
    val (text, labelColor) = when (status) {
        PaymentStatus.PAID -> "ĐÃ THANH TOÁN" to color.green
        PaymentStatus.UNPAID -> "CHƯA THANH TOÁN" to color.red
        PaymentStatus.PENDING -> "ĐANG XỬ LÝ" to color.orange
        PaymentStatus.OVERDUE -> "QUÁ HẠN" to color.orange
        PaymentStatus.CANCELLED -> "ĐÃ HỦY" to color.gray
    }

    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.SemiBold,
            color = labelColor,
            letterSpacing = TextUnit(value = 0.5f, type = TextUnitType.Sp)
        )
    )
}