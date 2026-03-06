package org.example.project.presentations.screen.school_schedule.components

import android.os.Build
import androidx.annotation.RequiresApi
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
import org.example.project.presentations.theme.LocalExtendedColors
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateList(
    modifier: Modifier = Modifier,
    selectedDayOfWeek: Int,
    onChangeDayOfWeek: (Int) -> Unit
) {
    val today = remember { LocalDate.now() }
    val dates = remember {
        val monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        (0..6).map { monday.plusDays(it.toLong()) }
    }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(dates) { date ->
            DateItem(
                day = date.dayOfWeek.value,
                dayNumber = date.dayOfMonth,
                isCurrent = date.dayOfWeek.value == selectedDayOfWeek,
                onClickItem = {
                    onChangeDayOfWeek(date.dayOfWeek.value)
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateItem(
    day: Int,
    dayNumber: Int,
    isCurrent: Boolean = false,
    onClickItem: () -> Unit
) {
    val dayText = if (day ==7) "CN" else "Th ${day + 1}"
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