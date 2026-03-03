package org.example.project.presentations.screen.timetable.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.example.project.presentations.theme.LocalExtendedColors

@Preview(showBackground = true)
@Composable
fun GridBackground(
    totalDays: Int = 6,
    totalPeriods: Int = 15,
    cellWidth: Dp = 100.dp,
    cellHeight: Dp = 60.dp,
    leftColumnWidth: Dp = 50.dp
) {
    Column {

        Row {

            Box(
                Modifier
                    .width(leftColumnWidth)
                    .height(cellHeight/2)
                    .border(0.5.dp, LocalExtendedColors.current.gray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Tiết",
                    style = MaterialTheme.typography.labelLarge,
                    color = LocalExtendedColors.current.mainBlue
                )
            }

            repeat(totalDays) { day ->
                Box(
                    Modifier
                        .width(cellWidth)
                        .height(cellHeight/2)
                        .border(0.5.dp, LocalExtendedColors.current.gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Thứ ${day + 2}",
                        style = MaterialTheme.typography.labelLarge,
                        color = LocalExtendedColors.current.mainBlue
                    )
                }
            }
        }

        repeat(totalPeriods) { period ->
            Row {
                Box(
                    Modifier
                        .width(leftColumnWidth)
                        .height(cellHeight)
                        .border(0.5.dp, LocalExtendedColors.current.gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "${period + 1}",
                        style = MaterialTheme.typography.labelLarge,
                        color = LocalExtendedColors.current.mainBlue
                    )
                }

                repeat(totalDays) {
                    Box(
                        Modifier
                            .width(cellWidth)
                            .height(cellHeight)
                            .border(0.5.dp, LocalExtendedColors.current.gray)
                    )
                }
            }
        }
    }
}