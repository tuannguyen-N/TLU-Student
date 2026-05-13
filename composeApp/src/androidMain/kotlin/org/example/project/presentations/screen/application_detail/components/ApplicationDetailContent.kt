package org.example.project.presentations.screen.application_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.data.mapper.toCreatedDate
import org.example.project.data.remote.dto.application_detail.ApplicationDetailData
import org.example.project.domain.model.ApplicationStatus
import org.example.project.presentations.screen.feedback.components.FeedbackStatusBadge
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun ApplicationDetailContent(
    data: ApplicationDetailData,
    onOpenUrl: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
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
                            text = data.typeName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        FeedbackStatusBadge(
                            status = try {
                                ApplicationStatus.valueOf(data.status)
                            } catch (e: Exception) {
                                ApplicationStatus.PENDING
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Ngày gửi: ${data.createdAt.toCreatedDate()}",
                        style = MaterialTheme.typography.bodySmall,
                        color = LocalExtendedColors.current.gray
                    )

                    data.content?.let {
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
                        )
                    }

                    data.attachments?.takeIf { it.isNotEmpty() }?.let { attachments ->
                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Tệp đính kèm",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            attachments.forEach { attachment ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFFF5F5F5))
                                        .clickable {
                                            onOpenUrl("https://res.cloudinary.com/dm5ev1isi/raw/upload/${attachment.fileKey}")
                                        }
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.InsertDriveFile,
                                        contentDescription = "File",
                                        tint = LocalExtendedColors.current.mainRed,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = attachment.originalFilename,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Black,
                                        maxLines = 1,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Nếu bạn vẫn gặp vấn đề, vui lòng liên hệ 0931910JQK để được hỗ trợ nhanh nhất.",
                style = MaterialTheme.typography.bodySmall,
                color = LocalExtendedColors.current.gray,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )
        }
    }
}