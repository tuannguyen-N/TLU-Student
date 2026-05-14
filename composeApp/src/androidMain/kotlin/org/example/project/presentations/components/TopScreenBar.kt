package org.example.project.presentations.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.R
import org.example.project.presentations.screen.timetable.components.DropDownPopup
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun <T> TopScreenBar(
    title: String,
    values: List<T> = emptyList(),
    value: String = "",
    justView: Boolean = false,
    enableListItem: Boolean = false,
    backgroundColor: Color = LocalExtendedColors.current.mainRed,
    contentColor: Color = Color.White,
    onBack: () -> Unit = {},
    onClickItem: (T) -> Unit = {},
    itemDisplay: (T) -> String = { it.toString() }
) {
    var showTermYear by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .background(backgroundColor)
            .statusBarsPadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_back),
                    contentDescription = null,
                    tint = contentColor
                )
            }

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = contentColor
            )

            Spacer(modifier = Modifier.weight(1f))

            if (enableListItem) {
                Box {
                    TermYear(
                        academicYear = value,
                        onClick = { showTermYear = true },
                        modifier = Modifier.padding(end = 12.dp),
                        justView = justView
                    )

                    if (showTermYear && !justView) {
                        DropDownPopup(
                            items = values,
                            selectedItem = value,
                            onClickItem = { onClickItem(it as T) },
                            onDismiss = { showTermYear = false },
                            alignment = Alignment.TopStart,
                            width = 180.dp,
                            itemDisplay = itemDisplay as (Any?) -> String,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TermYear(
    academicYear: String,
    onClick: () -> Unit,
    justView: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        color = Color(0x4DFFFFFF),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(
            0.7.dp,
            color = Color(0x1AFFFFFF)
        ),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(vertical = 3.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = academicYear,
                color = Color.White,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.widthIn(max = 100.dp)
            )

            if (!justView) {
                Icon(
                    painter = painterResource(R.drawable.icon_down),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}