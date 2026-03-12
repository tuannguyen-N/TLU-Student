package org.example.project.presentations.screen.timetable.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.R
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun WeekView(
    modifier: Modifier = Modifier,
    week: String = "Thứ 2",
    weekDate: String = "123123-123123",
    onClickPreviousWeek: () -> Unit = {},
    onClickNextWeek: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconView(
            R.drawable.icon_left,
            onClick = {
                onClickPreviousWeek()
            }
        )

        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = week,
                color = LocalExtendedColors.current.gray,
                style = MaterialTheme.typography.labelLarge
            )

            Text(
                text = weekDate,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        IconView(
            R.drawable.icon_right,
            onClick = {
                onClickNextWeek()
            }
        )
    }
}

@Composable
fun IconView(
    iconRes: Int,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = LocalExtendedColors.current.gray,
        )
    }
}