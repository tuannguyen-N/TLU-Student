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
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconView(
            R.drawable.icon_left,
            onClick = {
                // TODO:
            }
        )

        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Tuần 10",
                color = LocalExtendedColors.current.gray,
                style = MaterialTheme.typography.labelLarge
            )

            Text(
                text = "09/02 - 15/02/2026",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        IconView(
            R.drawable.icon_right,
            onClick = {
                // TODO:
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