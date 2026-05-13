package org.example.project.presentations.screen.application.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.example.project.data.mapper.toCreatedDate
import org.example.project.data.remote.dto.application_history.ApplicationHistoryData
import org.example.project.domain.model.ApplicationStatus
import org.example.project.presentations.screen.feedback.components.FeedbackStatusBadge
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun ApplicationHistoryCard(
    item: ApplicationHistoryData,
    onViewDetail: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.typeName,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                FeedbackStatusBadge(
                    status = try {
                        ApplicationStatus.valueOf(item.status)
                    } catch (e: Exception) {
                        ApplicationStatus.PENDING
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.createdAt.toCreatedDate(),
                    style = MaterialTheme.typography.bodySmall,
                    color = LocalExtendedColors.current.gray
                )
                Row(
                    modifier = Modifier.clickable { onViewDetail(item.id.toString()) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Xem chi tiết",
                        style = MaterialTheme.typography.bodySmall,
                        color = LocalExtendedColors.current.mainRed,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = LocalExtendedColors.current.mainRed,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}