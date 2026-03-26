package org.example.project.presentations.screen.tuition_payment.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.presentations.components.LabelView
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.toFormatAmount
import java.text.NumberFormat
import java.util.Locale


@Composable
fun TuitionCard(
    modifier: Modifier = Modifier,
    color: ExtendedColors = LocalExtendedColors.current,
    semester: String = "HK1 - 2024",
    totalAmount: Long = 24_500_000L,
    deadline: String = "03/06/2026",
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = color.mainBlue
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$semester",
                    style = MaterialTheme.typography.bodyMedium,
                    color = color.grayButton,
                    fontWeight = FontWeight.Normal
                )

                LabelView(
                    text = "Hạn: $deadline",
                    backgroundColor = color.red,
                    textColor = color.white
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tổng học phí cần đóng",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = totalAmount.toFormatAmount(),
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = Color.White,
                )

                Text(
                    text = "đ",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = color.red,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEEEEE)
@Composable
private fun TuitionCardPreview() {
    TuitionCard(
        modifier = Modifier.padding(16.dp),
        semester = "HK1 - 2024",
        totalAmount = 24_500_000L,
        deadline = "03/06/2026"
    )
}