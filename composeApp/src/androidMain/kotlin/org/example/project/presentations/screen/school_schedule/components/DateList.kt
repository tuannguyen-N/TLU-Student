package org.example.project.presentations.screen.school_schedule.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun DateList(
    modifier: Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(6) { index ->
            DateItem(
                day = "Th 2",
                dayNumber = 14 + index,
                isCurrent = index == 2,
                onClickItem = {}
            )
        }
    }
}

@Composable
fun DateItem(
    day: String,
    dayNumber: Int,
    isCurrent: Boolean = false,
    onClickItem: () -> Unit
) {

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
        modifier = Modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {

            Text(
                text = day,
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

