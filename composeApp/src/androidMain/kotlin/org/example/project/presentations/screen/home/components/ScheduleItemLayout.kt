package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.data.mapper.toHourMinuteAmPm
import org.example.project.presentations.theme.ExtendedColors

@Composable
fun ScheduleItemLayout(
    modifier: Modifier = Modifier,
    color: ExtendedColors,
    startTime: String,
    endTime: String,
    isGoing: Boolean,
    isFirstItem: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    val dotColor = if (isGoing) color.green else color.gray
    val lineColor = color.gray
    val timeColor = if (isGoing) Color.Unspecified else color.gray
    val topPadding = if (isGoing) 3.dp else 15.dp

    Row(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(top = topPadding),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = startTime.toHourMinuteAmPm(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = timeColor
            )
            Text(
                text = endTime.toHourMinuteAmPm(),
                style = MaterialTheme.typography.bodyMedium,
                color = timeColor
            )
        }

        Spacer(modifier = Modifier.width(34.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(2.dp),
            modifier = Modifier
                .weight(1f)
                .padding(
                    top = if (isGoing) 0.dp else 15.dp,
                    bottom = if (isGoing) 0.dp else 5.dp
                )
                .drawBehind {
                    val dotRadius = 3.5.dp.toPx()
                    val startX = -12.dp.toPx() - dotRadius

                    if (isGoing) {
                        val topOffset = 7.dp.toPx()
                        drawCircle(
                            color = dotColor,
                            radius = dotRadius,
                            center = Offset(startX, topOffset + dotRadius)
                        )
                        drawLine(
                            color = lineColor,
                            start = Offset(startX, topOffset + dotRadius * 2),
                            end = Offset(startX, size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                    } else {
                        val cardTopOffset = -15.dp.toPx()
                        val dotTopOffset = cardTopOffset + 23.dp.toPx()
                        if (!isFirstItem) {
                            drawLine(
                                color = lineColor,
                                start = Offset(startX, cardTopOffset),
                                end = Offset(startX, size.height + 5.dp.toPx()),
                                strokeWidth = 1.dp.toPx()
                            )
                        } else {
                            drawLine(
                                color = lineColor,
                                start = Offset(startX, dotTopOffset + dotRadius * 2),
                                end = Offset(startX, size.height + 5.dp.toPx()),
                                strokeWidth = 1.dp.toPx()
                            )
                        }
                        drawCircle(
                            color = dotColor,
                            radius = dotRadius,
                            center = Offset(startX, dotTopOffset + dotRadius)
                        )
                    }
                }
        ) {
            Column(
                modifier = Modifier
                    .background(if (isGoing) color.white else Color(0xFFF7F7F7))
                    .padding(horizontal = 12.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                content = content
            )
        }
    }
}