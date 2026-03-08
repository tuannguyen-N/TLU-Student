package org.example.project.presentations.screen.school_schedule.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.example.project.presentations.theme.LocalExtendedColors
import kotlin.time.Clock

@Composable
fun DateList(
    modifier: Modifier = Modifier,
    selectedDayOfWeek: Int,
    onChangeDayOfWeek: (Int) -> Unit
) {
    val today = remember {
        Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
    }

    val dates = remember {
        val monday = today.minus((today.dayOfWeek.isoDayNumber - 1), DateTimeUnit.DAY)

        (0..6).map { monday.plus(it, DateTimeUnit.DAY) }
    }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(dates) { date ->
            DateItem(
                day = date.dayOfWeek.isoDayNumber,
                dayNumber = date.dayOfMonth,
                isCurrent = date.dayOfWeek.isoDayNumber == selectedDayOfWeek,
                onClickItem = {
                    onChangeDayOfWeek(date.dayOfWeek.isoDayNumber)
                }
            )
        }
    }
}

@Composable
fun DateItem(
    day: Int,
    dayNumber: Int,
    isCurrent: Boolean = false,
    onClickItem: () -> Unit
) {
    val dayText = remember(day) {
        if (day == 7) "CN" else "Th ${day + 1}"
    }

    val backgroundColor =
        if (isCurrent) LocalExtendedColors.current.mainBlue
        else Color.White

    val textColor =
        if (isCurrent) Color.White
        else Color.Gray

    Surface(
        onClick = onClickItem,
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = dayText,
                color = textColor,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = dayNumber.toString(),
                color = textColor,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}