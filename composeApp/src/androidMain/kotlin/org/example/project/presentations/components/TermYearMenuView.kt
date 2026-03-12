package org.example.project.presentations.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun TermYearMenuView(
    schoolYears: List<String> = listOf("2023-2024", "2022-2023", "2021-2022"),
    onClickSchoolYear: (String) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    var itemHeightPx by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current

    val boxHeight = if (itemHeightPx > 0) {
        with(density) { (itemHeightPx * 8).toDp() }
    } else Dp.Unspecified
    Popup(
        alignment = Alignment.TopEnd,
        offset = IntOffset(x = -20, y = 20),
        onDismissRequest = onDismiss
    ){
        Box(
            modifier = Modifier
                .width(150.dp)
                .then(if (boxHeight != Dp.Unspecified) Modifier.height(boxHeight) else Modifier)
                .shadow(elevation = 12.dp, shape = RoundedCornerShape(5.dp))
                .background(color = Color.White, shape = RoundedCornerShape(5.dp))
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                itemsIndexed(schoolYears) {index, schoolYear ->
                    Surface(
                        onClick = {
                            onClickSchoolYear(schoolYear)
                        },
                        color = Color.White
                    ) {
                        Text(
                            text = schoolYear,
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                                .fillMaxWidth()
                                .then(
                                    if (index == 0) Modifier.onGloballyPositioned {
                                        itemHeightPx = it.size.height
                                    } else Modifier
                                ),
                            textAlign = TextAlign.Center
                        )
                    }

                    if (index != 9)
                        HorizontalDivider(color = LocalExtendedColors.current.gray, thickness = 0.5.dp)

                }
            }
        }
    }
}