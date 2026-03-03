package org.example.project.presentations.dialog

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.example.project.R
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun SubjectDetailDialog(
    onDismiss: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        ) {

            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            LocalExtendedColors.current.red,
                            RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                        )
                        .padding(18.dp)
                ) {
                    Text(
                        text = "Tiếng Anh Trung cấp 2",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }


                Column(
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {

                    InfoRow(
                        iconRes = R.drawable.icon_detail_location_subject,
                        title = "Phòng học",
                        value = "B605"
                    )

                    HorizontalDivider()

                    InfoRow(
                        iconRes = R.drawable.icon_detail_time_subjet,
                        title = "Thời gian",
                        value = "Tiết 1 - 3",
                        subValue = "07:00 AM - 09:15 AM"
                    )

                    HorizontalDivider()

                    InfoRow(
                        iconRes = R.drawable.icon_detail_teacher_subject,
                        title = "Giáo viên",
                        value = "ThS. Nguyễn Văn Tày"
                    )

                    HorizontalDivider()

                    InfoRow(
                        iconRes = R.drawable.icon_detail_email_subject,
                        title = "Email",
                        value = "500274@thanglong.edu.vn"
                    )

                    HorizontalDivider()

                    InfoRow(
                        iconRes = R.drawable.icon_detail_code_subject,
                        title = "Mã học phần",
                        value = "ENG202-01"
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    ButtonView(
                        text = "Đóng",
                        modifier = Modifier.height(40.dp),
                        backgroundColorRes = LocalExtendedColors.current.red,
                        textColorRes = Color.White,
                        onClick = onDismiss
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun InfoRow(
    iconRes: Int,
    title: String,
    value: String,
    subValue: String? = null
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {

            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = LocalExtendedColors.current.gray
            )

            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = LocalExtendedColors.current.mainBlue
            )

            if (subValue != null) {
                Text(
                    text = subValue,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}