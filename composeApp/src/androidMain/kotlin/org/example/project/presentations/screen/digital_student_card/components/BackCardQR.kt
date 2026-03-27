package org.example.project.presentations.screen.digital_student_card.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.theme.LocalExtendedColors

@Preview(showBackground = true)
@Composable
fun BackCardQR(
    modifier: Modifier = Modifier,
    name: String = "Nguyen Van Tay",
    timeLeft: Int = 120,
    qrBitmap: ByteArray = byteArrayOf(),
    onBack: () -> Unit = {},
    onRegenerateQr: () -> Unit = {},
) {
    val color = LocalExtendedColors.current
    val isExpired = timeLeft == 0

    val imageBitmap = remember(qrBitmap) {
        if (qrBitmap.isNotEmpty()) {
            BitmapFactory.decodeByteArray(qrBitmap, 0, qrBitmap.size)
                ?.asImageBitmap()
        } else {
            null
        }
    }

    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val timeText = "%d:%02d".format(minutes, seconds)
    val progress = timeLeft / 120f

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isExpired) {
            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(color.red.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_qr_code),
                    contentDescription = null,
                    tint = color.red,
                    modifier = Modifier.size(44.dp)
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Mã QR đã hết hạn",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Vui lòng tạo mã mới để tiếp tục sử dụng",
                style = MaterialTheme.typography.bodyMedium,
                color = color.gray,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            ButtonView(
                text = "Tạo QR mới",
                enabled = true,
                iconRes = R.drawable.icon_refresh,
                onClick = onRegenerateQr,
                backgroundColorRes = color.lightBlue
            )
        } else {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )

            Spacer(Modifier.height(16.dp))
            imageBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    modifier = Modifier.size(220.dp)
                )
            }

            Spacer(Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Hết hạn sau: ",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                Text(
                    text = timeText,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = color.red
                )
            }

            Spacer(Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(50)),
                color = color.lightBlue,
                trackColor = color.gray.copy(alpha = 0.2f)
            )

            Spacer(Modifier.height(18.dp))

            Text(
                text = "Không chia sẻ mã QR này cho người khác",
                style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                color = color.gray
            )
        }

        Spacer(Modifier.height(18.dp))

        HorizontalDivider(color = color.gray.copy(alpha = 0.15f))

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .clickable(onClick = onBack)
                .clip(RoundedCornerShape(12.dp))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = color.grayNavy,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = "Quay lại thẻ sinh viên",
                style = MaterialTheme.typography.bodyMedium,
                color = color.grayNavy
            )
        }

        Spacer(Modifier.height(8.dp))
    }
}