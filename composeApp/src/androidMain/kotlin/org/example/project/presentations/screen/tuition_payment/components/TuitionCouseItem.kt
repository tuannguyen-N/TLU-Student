package org.example.project.presentations.screen.tuition_payment.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.presentations.components.DashedDivider
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun TuitionCourseItem(
    modifier: Modifier = Modifier,
    color: ExtendedColors,
    courseCode: String,
    courseName: String,
    credits: Int,
    isLastItem: Boolean,
    amount: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = courseCode,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = color.blackBackground
                )

                Row {
                    Text(
                        text = courseName,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = color.blackBackground,
                        modifier = Modifier.weight(1f),
                    )

                    Spacer(Modifier.width(10.dp))

                    Text(
                        text = amount,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = color.mainBlue,
                        maxLines = 1
                    )
                }

                Text(
                    text = "$credits Tín chỉ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = color.gray
                )
            }
        }

        if (!isLastItem) {
            DashedDivider(
                color = LocalExtendedColors.current.gray,
                strokeWidth = 0.3.dp,
                dashLength = 5.dp,
                gapLength = 5.dp
            )
        }
    }
}