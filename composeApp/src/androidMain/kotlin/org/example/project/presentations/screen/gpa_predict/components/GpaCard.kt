package org.example.project.presentations.screen.gpa_predict.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun GpaCard(
    modifier: Modifier = Modifier,
    title: String,
    gpa: Double?,
    creditsEarned: Int?,
    totalCredits: Int,
    isDark: Boolean = false
) {
    val color = LocalExtendedColors.current

    val containerColor = if (isDark) color.mainBlue else color.white
    val titleColor = if (isDark) color.white.copy(alpha = 0.7f) else color.gray
    val gpaColor = if (isDark) color.red else color.mainBlue
    val slashColor = if (isDark) color.white.copy(alpha = 0.5f) else color.gray
    val creditColor = if (isDark) color.white else color.gray

    Card(
        shape = RoundedCornerShape(14.dp),
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = BorderStroke(1.dp, color.gray.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = titleColor,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = gpa?.let { "%.2f".format(it) } ?: "???", color = gpaColor,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(Modifier.width(2.dp))
                Text(
                    text = "/ 4.0",
                    color = slashColor,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            Spacer(Modifier.height(6.dp))

            Text(
                text = "${creditsEarned ?: "???"}/$totalCredits TÍN CHỈ",
                color = creditColor,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp
                )
            )
        }
    }
}