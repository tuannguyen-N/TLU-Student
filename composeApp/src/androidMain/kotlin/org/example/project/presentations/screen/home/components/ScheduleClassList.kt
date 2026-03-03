package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.ScheduleClassUiModel
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun ScheduleClassList(
    modifier: Modifier = Modifier,
    onClickAll: () -> Unit = {},
    items: List<ScheduleClassUiModel> = ScheduleClassUiModel.getDataDemo()
) {
    Column {
        Row(
            modifier = modifier
                .padding(bottom = 15.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Lịch học hôm nay",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Tất cả",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = LocalExtendedColors.current.fontBlue
            )
        }

        items.forEach { item ->
            if (item.isCurrent) {
                ScheduleCurrent(
                    modifier = modifier,
                    item = item
                )
            } else {
                ScheduleNext(
                    modifier = modifier,
                    item = item
                )
            }
        }
    }
}