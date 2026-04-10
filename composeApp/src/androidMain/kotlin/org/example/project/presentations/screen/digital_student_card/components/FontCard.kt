package org.example.project.presentations.screen.digital_student_card.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.example.project.R
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun FrontCard(
    name: String,
    studentCode: String,
    faculty: String,
    birthDate: String,
    course: String,
    avatarUrl: String?,
    onCreateQr: () -> Unit
) {
    val color = LocalExtendedColors.current

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.bg_digital_card_student),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 40.dp)
            ) {
                if (avatarUrl == "" || avatarUrl.isNullOrEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(LocalExtendedColors.current.gray)
                            .border(
                                BorderStroke(1.dp, Color.White), CircleShape
                            )
                            .padding(20.dp)
                    )
                } else {
                    AsyncImage(
                        model = avatarUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.White, CircleShape)
                    )
                }
            }
        }

        Spacer(Modifier.height(55.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(24.dp))

        HorizontalDivider(
            color = color.gray.copy(alpha = 0.15f),
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            StudentInfoRow(label = "Mã sinh viên", value = studentCode)
            StudentInfoRow(label = "Khoa", value = faculty)
            StudentInfoRow(label = "Ngày sinh", value = birthDate)
            StudentInfoRow(label = "Khoá học", value = course)
        }

        Spacer(Modifier.height(18.dp))

        ButtonView(
            text = "Tạo mã QR",
            backgroundColorRes = color.red,
            textColorRes = Color.White,
            iconRes = R.drawable.ic_qr_code,
            enabled = true,
            onClick = onCreateQr,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(horizontal = 34.dp)
        )

        Spacer(Modifier.height(30.dp))
    }
}

@Composable
private fun StudentInfoRow(
    label: String,
    value: String
) {
    val color = LocalExtendedColors.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = color.gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = Color.Black
        )
    }
}