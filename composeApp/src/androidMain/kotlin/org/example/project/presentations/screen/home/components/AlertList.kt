package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.AlertUiModel
import org.example.project.presentations.components.shimmerBrush

@Preview
@Composable
fun AlertList(
    modifier: Modifier = Modifier,
    items: List<AlertUiModel> = AlertUiModel.getDemoList(),
    isLoading: Boolean = false,
    onClickAction: () -> Unit = {}
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (isLoading) {
            items(count = 3, key = { "shimmer_alert_$it" }) {
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(130.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(shimmerBrush())
                )
            }
        } else {
            itemsIndexed(
                items = items,
                key = { index, item -> "${index}_${item.title}" },
                contentType = { _, item -> if (item.isHighAlert) "HighAlert" else "MediumAlert" }
            ) { _, item ->
                if (item.isHighAlert) {
                    HighAlertCard(item = item, onClickAction = onClickAction)
                } else {
                    MediumAlertCard(item = item, onClickAction = onClickAction)
                }
            }
        }
    }
}