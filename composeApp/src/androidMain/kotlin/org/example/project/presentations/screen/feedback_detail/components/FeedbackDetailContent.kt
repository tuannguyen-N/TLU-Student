package org.example.project.presentations.screen.feedback_detail.components

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.example.project.domain.model.FeedbackItem
import org.example.project.domain.model.FeedbackStatus
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.screen.feedback.components.FeedbackStatusBadge
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun FeedbackDetailContent(
    modifier: Modifier = Modifier
) {
    val item = FeedbackItem(
        id = "1",
        title = "Lỗi thanh toán",
        preview = "",
        date = "10/05/2026",
        status = FeedbackStatus.RESOLVED
    )
    val fullContent =
        "Tôi đã thanh toán đơn hàng mã #12345 qua ví điện tử nhưng hệ thống vẫn báo chưa thanh toán. Tôi đã bị trừ tiền trong tài khoản ngân hàng. Vui lòng kiểm tra và cập nhật trạng thái đơn hàng giúp tôi. Xin cảm ơn!"
    val attachedImages = listOf(
        "https://picsum.photos/seed/beach/200/150",
        "https://picsum.photos/seed/books/200/150",
        "https://picsum.photos/seed/receipt/200/150"
    )

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
                            text = item.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        FeedbackStatusBadge(status = item.status)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Ngày gửi: ${item.date}",
                        style = MaterialTheme.typography.bodySmall,
                        color = LocalExtendedColors.current.gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = fullContent,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black,
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Ảnh đính kèm",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        attachedImages.forEach { imageUrl ->
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFF0F0F0))
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Nếu bạn vẫn gặp vấn đề, vui lòng liên hệ tổng đài 1900xxxx để được hỗ trợ nhanh nhất.",
                style = MaterialTheme.typography.bodySmall,
                color = LocalExtendedColors.current.gray,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )
        }

        ButtonView(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            text = "Gỡ phản hồi",
            backgroundColorRes = LocalExtendedColors.current.mainRed,
            textColorRes = Color.White,
            onClick = { /*TODO*/ },
            enabled = item.status == FeedbackStatus.PENDING
        )
    }
}