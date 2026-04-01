package org.example.project.presentations.screen.tuition_payment.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.presentations.components.LabelHeader
import org.example.project.presentations.theme.ExtendedColors

enum class PaymentStatus { SUCCESS, FAILED }

data class PaymentItem(
    val title: String,
    val date: String,
    val amount: String,
    val status: PaymentStatus
)

data class SemesterGroup(
    val semesterLabel: String,
    val items: List<PaymentItem>
)

@Composable
fun PaymentHistoryContent(color: ExtendedColors) {

    val semesters = listOf(
        SemesterGroup(
            semesterLabel = "Học kỳ II (2023 - 2024)",
            items = listOf(
                PaymentItem(
                    title = "Học phí chính quy",
                    date = "15/03/2024",
                    amount = "8.200.000đ",
                    status = PaymentStatus.SUCCESS
                ),
                PaymentItem(
                    title = "Lệ phí thi lại",
                    date = "10/03/2024 • 09:15",
                    amount = "450.000đ",
                    status = PaymentStatus.FAILED
                )
            )
        ),
        SemesterGroup(
            semesterLabel = "Học kỳ I (2023 - 2024)",
            items = listOf(
                PaymentItem(
                    title = "Học phí bổ sung",
                    date = "22/12/2023 • 16:45",
                    amount = "2.100.000đ",
                    status = PaymentStatus.FAILED
                ),
                PaymentItem(
                    title = "Học phí chính quy",
                    date = "15/09/2023 • 10:00",
                    amount = "7.800.000đ",
                    status = PaymentStatus.SUCCESS
                ),
                PaymentItem(
                    title = "Bảo hiểm y tế",
                    date = "05/09/2023 • 08:20",
                    amount = "702.000đ",
                    status = PaymentStatus.SUCCESS
                )
            )
        )
    )

    LazyColumn {
        semesters.forEach { semester ->
            item { LabelHeader(label = semester.semesterLabel) }

            items(semester.items) { payment ->
                PaymentCard(item = payment, color = color)
            }

            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun PaymentCard(item: PaymentItem, color: ExtendedColors) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color.white)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatusIcon(status = item.status, color = color)

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium,
                        color = color.blackBackground
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = item.date,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = color.gray
                    )
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = item.amount,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = color.blackBackground
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                StatusLabel(status = item.status, color = color)
            }
        }
    }
}

@Composable
private fun StatusIcon(status: PaymentStatus, color: ExtendedColors) {
    val bgColor =
        if (status == PaymentStatus.SUCCESS) color.green.copy(alpha = 0.2f) else color.red.copy(
            alpha = 0.2f
        )
    val iconColor = if (status == PaymentStatus.SUCCESS) color.green else color.red
    val icon = if (status == PaymentStatus.SUCCESS) Icons.Filled.Check else Icons.Filled.Close

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
    val text = if (status == PaymentStatus.SUCCESS) "THÀNH CÔNG" else "THẤT BẠI"
    val color = if (status == PaymentStatus.SUCCESS) color.green else color.red

    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.SemiBold,
            color = color,
            letterSpacing = androidx.compose.ui.unit.TextUnit(
                value = 0.5f,
                type = androidx.compose.ui.unit.TextUnitType.Sp
            )
        )
    )
}