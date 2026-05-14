package org.example.project.presentations.screen.digital_student_card.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.presentations.components.Base64Image
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun FrontCard(
    name: String,
    studentCode: String,
    faculty: String,
    birthDate: String,
    course: String,
    classCode: String,
    avatarUrl: String?,
    majorCode: String,
    onCreateQr: () -> Unit
) {
    val backgroundColor = when (majorCode) {
        "TA", "TI", "TE", "TT", "IT" -> Color(0xFF1D53E6)
        "EC", "IE", "EL", "MK", "AC", "FN", "LG", "BA" -> Color(0xFF16A634)
        "NU" -> Color(0xFF3AC2D8)
        "EN", "KR", "CN", "JP", "VN" -> Color(0xFFD43A26)
        "TM", "HM" -> Color(0xFF00715F)
        "MM" -> Color(0xFFFFA400)
        "VO" -> Color(0xFF6F00FF)
        else -> Color(0xFF1D53E6)
    }

    val logoFactory = when (majorCode) {
        "TA", "TI", "TE", "TT", "IT" -> R.drawable.toan_tin
        "EC", "IE", "EL", "MK", "AC", "FN", "LG", "BA" -> R.drawable.kinh_te
        "NU" -> R.drawable.suc_khoe
        "EN", "KR", "CN", "JP", "VN" -> R.drawable.ngoai_ngu
        "TM", "HM" -> R.drawable.du_lich
        "MM" -> R.drawable.truyen_thong
        "VO" -> R.drawable.am_nhac
        else -> R.drawable.toan_tin
    }

    val color = LocalExtendedColors.current
    Column(
        modifier = Modifier
            .width(340.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            if (avatarUrl.isNullOrEmpty()) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color(0xFF90A4AE),
                    modifier = Modifier.size(80.dp)
                )
            } else {
                Base64Image(
                    base64String = avatarUrl,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(horizontal = 20.dp)
        ) {

            InfoRow("Sinh viên", name.uppercase(), isName = true)
            InfoRow("Ngày sinh", birthDate)
            InfoRow("Ngành", faculty)
            InfoRow("Lớp", classCode)
            InfoRow("Niên khoá", course)
            InfoRow("MSV", studentCode, isLast = true)

            Spacer(Modifier.height(5.dp))
        }

        Box {
            Image(
                painter = painterResource(logoFactory),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().scale(1.01f),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = onCreateQr,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterEnd)
                    .clip(
                        CircleShape
                    )
                    .background(color.white)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_qr_code),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = color.red
                )
            }
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    isLast: Boolean = false,
    isName: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 9.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.65f),
            modifier = Modifier.width(80.dp)
        )
        Text(
            text = value,
            style = if (isName) MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold) else MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
    }
    if (!isLast) HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
}

@Preview(showBackground = true)
@Composable
private fun FrontCardPreview() {
    MaterialTheme {
        FrontCard(
            name = "Nguyễn Văn A",
            studentCode = "B21DCCN001",
            faculty = "Công nghệ thông tin",
            birthDate = "01/01/2003",
            course = "K21",
            avatarUrl = null,
            onCreateQr = {},
            classCode = "1123",
            majorCode = "KHMT"
        )
    }
}