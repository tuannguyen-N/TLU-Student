package org.example.project.presentations.screen.alerts_and_actions.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.data.mapper.toAlertActionItem
import org.example.project.domain.model.AlertActionItem
import org.example.project.domain.model.AlertPriority
import org.example.project.domain.model.AlertUiModel
import org.example.project.domain.model.NotificationReferenceType
import org.example.project.domain.model.NotificationSeverity
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.components.TopScreenBar
import org.example.project.presentations.theme.LocalExtendedColors

@Preview(showBackground = true)
@Composable
fun AlertsAndActionsContent(
    onBack: () -> Unit = {},
    items: List<AlertUiModel> = mockAlertItems,
    onAction: (NotificationReferenceType) -> Unit = {}
) {
    val color = LocalExtendedColors.current

    Scaffold(
        topBar = {
            TopScreenBar<String>(
                title = "Cảnh báo và hành động",
                onBack = onBack,
                backgroundColor = color.white,
                contentColor = color.mainRed,
                enableListItem = false
            )
        },
        containerColor = color.background,
        contentWindowInsets = WindowInsets(0)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    Text(
                        text = "Danh sách cảnh báo",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = color.blackBackground
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Hoàn thành các mục dưới đây để đảm bảo quá trình học tập không bị gián đoạn.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = color.gray
                    )
                }
            }

            items(items.map { it.toAlertActionItem() }) { item ->
                AlertActionCard(
                    item = item,
                    onActionClick = { onAction(item.referenceType) }
                )
            }
        }
    }
}

val mockAlertItems = listOf(
    AlertUiModel(
        title = "Nộp học phí kỳ 2",
        content = "Vui lòng hoàn tất thanh toán để tránh bị hủy đăng ký môn học.",
        severity = NotificationSeverity.WARNING,
        notificationReferenceType = NotificationReferenceType.TUITION,
        deadline = "Hạn: 25/08",
        daysUntil = 10
    ),
    AlertUiModel(
        title = "Khảo sát chất lượng giảng dạy",
        content = "Đánh giá các học phần đã học trong học kỳ qua.",
        severity = NotificationSeverity.UPCOMING,
        notificationReferenceType = NotificationReferenceType.EXAM_SCHEDULE,
        deadline = "Hạn: 30/09",
        daysUntil = 10
    ),
    AlertUiModel(
        title = "Nộp học phí kỳ 1",
        content = "Học phí kỳ 1 đã quá hạn thanh toán. Vui lòng liên hệ phòng tài vụ.",
        severity = NotificationSeverity.OVERDUE,
        notificationReferenceType = NotificationReferenceType.TUITION,
        deadline = "Hết hạn: 15/07",
        daysUntil = 10
    ),
    AlertUiModel(
        title = "Đăng ký môn học kỳ 2",
        content = "Bạn đã hoàn thành đăng ký môn học cho kỳ 2 thành công.",
        severity = NotificationSeverity.COMPLETED,
        notificationReferenceType = NotificationReferenceType.EXAM_SCHEDULE,
        deadline = "Hoàn thành: 10/08",
        daysUntil = 10
    )
)

@Composable
fun AlertActionCard(
    item: AlertActionItem,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val color = LocalExtendedColors.current

    val accentColor = when (item.priority) {
        AlertPriority.URGENT -> color.mainRed
        AlertPriority.NEW -> color.orange
        AlertPriority.INFO -> color.gray
        AlertPriority.OVERDUE -> Color(0xFFB71C1C)
        AlertPriority.COMPLETED -> Color(0xFF2E7D32)
    }

    val tagBackground = accentColor.copy(alpha = 0.12f)

    val isOverdue = item.priority == AlertPriority.OVERDUE
    val isCompleted = item.priority == AlertPriority.COMPLETED
    val isUrgent = item.priority == AlertPriority.URGENT

    val cardAlpha = if (isCompleted) 0.6f else 1f

    Card(
        modifier = modifier
            .fillMaxWidth()
            .alpha(cardAlpha),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) color.background else color.white
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isCompleted) 0.dp else 1.dp
        ),
        border = if (isOverdue)
            BorderStroke(1.dp, Color(0xFFB71C1C).copy(alpha = 0.4f))
        else null
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(
                        color = accentColor,
                        shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                    )
                    .align(Alignment.CenterVertically)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Tag + Deadline
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = tagBackground
                    ) {
                        Text(
                            text = item.tag,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = accentColor
                        )
                    }

                    if (item.deadline.isNotBlank()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Alarm,
                                contentDescription = null,
                                tint = accentColor,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Hạn: ${item.deadline}",
                                style = MaterialTheme.typography.labelMedium,
                                color = accentColor,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.Top) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = item.priority.toIconBackgroundColor(),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(item.referenceType.toIconRes()),
                            contentDescription = null,
                            tint = item.priority.toIconTint(),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = color.blackBackground,
                            textDecoration = if (isCompleted)
                                TextDecoration.LineThrough else TextDecoration.None
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = color.gray
                        )
                    }

                    if (isCompleted) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                if (!isCompleted && item.actionLabel.isNotBlank()) {
                    Spacer(modifier = Modifier.height(16.dp))

                    if (isUrgent || isOverdue) {
                        ButtonView(
                            text = item.actionLabel,
                            backgroundColorRes = accentColor,
                            textColorRes = Color.White,
                            onClick = onActionClick,
                            enabled = true,
                            modifier = Modifier
                                .wrapContentWidth()
                                .height(44.dp)
                        )
                    } else {
                        OutlinedButton(
                            onClick = onActionClick,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = color.background,
                                contentColor = color.blackBackground
                            ),
                            border = BorderStroke(0.dp, Color.Transparent),
                            modifier = Modifier
                                .align(Alignment.End)
                                .height(44.dp)
                        ) {
                            Text(
                                text = item.actionLabel,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun AlertPriority.toIconBackgroundColor(): Color {
    return when (this) {
        AlertPriority.URGENT -> Color(0xFFFFEBEE)
        AlertPriority.NEW -> Color(0xFFFFEFE0)
        AlertPriority.INFO -> Color(0xFFF5F5F5)
        AlertPriority.OVERDUE -> Color(0xFFFFCDD2)
        AlertPriority.COMPLETED -> Color(0xFFE8F5E9)
    }
}

private fun AlertPriority.toIconTint(): Color {
    return when (this) {
        AlertPriority.URGENT -> Color(0xFFE53935)
        AlertPriority.NEW -> Color(0xFFF97416)
        AlertPriority.INFO -> Color(0xFF757575)
        AlertPriority.OVERDUE -> Color(0xFFB71C1C)
        AlertPriority.COMPLETED -> Color(0xFF2E7D32)
    }
}

private fun NotificationReferenceType.toIconRes(): Int {
    return when (this) {
        NotificationReferenceType.TUITION -> R.drawable.icon_caution
        NotificationReferenceType.EXAM_SCHEDULE -> R.drawable.icon_upcoming
    }
}