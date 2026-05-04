package org.example.project.presentations.screen.notification_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.data.mapper.toFullDisplayDate
import org.example.project.data.remote.dto.notification_detail.NotificationDetailData
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.components.TopScreenBar
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun NotificationDetailContent(
    notificationDetail: NotificationDetailData?,
    onBack: () -> Unit
) {
    val color = LocalExtendedColors.current
    StatusBarStyle(darkIcons = true)
    Scaffold(
        contentWindowInsets = WindowInsets(0),
        containerColor = color.background,
        topBar = {
            TopScreenBar<String>(
                title = "Chi tiết thông báo",
                backgroundColor = color.white,
                contentColor = color.blackBackground,
                onBack = onBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .padding(top = 20.dp),

            ) {
            Text(
                text = notificationDetail?.title.orEmpty(),
                color = color.blackBackground,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(Modifier.height(10.dp))

            Row {
                Text(
                    text = "Ngày tạo: ",
                    style = MaterialTheme.typography.bodySmall,
                    color = color.gray
                )

                Text(
                    text = notificationDetail?.createdAt?.toFullDisplayDate().orEmpty(),
                    style = MaterialTheme.typography.bodySmall,
                    color = color.blackBackground
                )
            }

            notificationDetail?.deadLine?.let {
                Spacer(Modifier.height(10.dp))

                Row {
                    Text(
                        text = "Hạn đến:  ",
                        style = MaterialTheme.typography.bodySmall,
                        color = color.gray
                    )

                    Text(
                        text = it.toFullDisplayDate(),
                        style = MaterialTheme.typography.bodySmall,
                        color = color.blackBackground
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Nội dung thông báo",
                style = MaterialTheme.typography.bodyLarge,
                color = color.blackBackground
            )
        }
    }
}