package org.example.project.presentations.screen.feedback.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.domain.model.FeedbackItem
import org.example.project.domain.model.FeedbackStatus
import org.example.project.presentations.theme.LocalExtendedColors

@Preview(showBackground = true)
@Composable
fun FeedbackHistoryContent(
    onCreateFeedback: () -> Unit = {},
    onViewDetail: (String) -> Unit = {}
) {
    val feedbackList = listOf(
        FeedbackItem(
            id = "1",
            title = "Lỗi đăng ký môn học",
            preview = "Tôi đã chọn môn 'Lập trình Android' trong kỳ Fall 2026 nhưng khi xác nhận đăng ký hệ thống báo lỗi...",
            date = "10/05/2026",
            status = FeedbackStatus.RESOLVED
        ),
        FeedbackItem(
            id = "2",
            title = "Góp ý giao diện",
            preview = "Font chữ ở màn hình lịch học hơi nhỏ, mong hệ thống có thể điều chỉnh to hơn để sinh viên dễ đọc...",
            date = "08/05/2026",
            status = FeedbackStatus.PENDING
        ),
        FeedbackItem(
            id = "3",
            title = "Vấn đề thông tin cá nhân",
            preview = "Không thể cập nhật số điện thoại trong phần thông tin sinh viên. Khi lưu thay đổi hệ thống báo lỗi...",
            date = "01/05/2026",
            status = FeedbackStatus.PROCESSING
        ),
        FeedbackItem(
            id = "4",
            title = "Sai thông tin học phí",
            preview = "Trong mục học phí kỳ này hệ thống hiển thị số tiền cao hơn so với thông báo từ phòng đào tạo...",
            date = "25/04/2026",
            status = FeedbackStatus.RESOLVED
        ),
        FeedbackItem(
            id = "5",
            title = "Không hiển thị lịch thi",
            preview = "Trong mục lịch thi chưa hiển thị lịch thi của môn 'Cấu trúc dữ liệu', mong nhà trường kiểm tra...",
            date = "20/04/2026",
            status = FeedbackStatus.PROCESSING
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        if (feedbackList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Chưa có lịch sử phản hồi",
                    color = LocalExtendedColors.current.gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 8.dp, bottom = 88.dp)
            ) {
                items(feedbackList) { item ->
                    FeedbackHistoryCard(
                        item = item,
                        onViewDetail = onViewDetail
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = onCreateFeedback,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = LocalExtendedColors.current.mainRed,
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Tạo phản hồi"
            )
        }
    }
}
