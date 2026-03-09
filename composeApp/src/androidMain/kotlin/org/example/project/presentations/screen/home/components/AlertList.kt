package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.AlertUiModel
import org.example.project.presentations.components.shimmerBrush

@Composable
fun AlertList(
    items: List<AlertUiModel>,
    isLoading: Boolean = false,
    onClickAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth()
    ) {
        if (isLoading) {
            items(3) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .width(300.dp)
                        .height(130.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(shimmerBrush())
                )
            }
        } else {
            itemsIndexed(
                items = items,
                key = { index, _ -> index },
                contentType = { _, item -> item.isHighAlert }
            ) { _, item ->
                when {
                    item.isHighAlert -> {
                        HighAlertCard(
                            item = item,
                            onClickAction = onClickAction
                        )
                    }
                    else -> {
                        MediumAlertCard(
                            item = item,
                            onClickAction = onClickAction
                        )
                    }
                }
            }
        }
    }
}