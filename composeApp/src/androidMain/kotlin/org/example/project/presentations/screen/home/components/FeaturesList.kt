package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.FeatureUiModel
import org.example.project.presentations.components.shimmerBrush

@Preview
@Composable
fun FeatureList(
    modifier: Modifier = Modifier,
    items: List<FeatureUiModel> = FeatureUiModel.getDemoList(),
    onClickItem: (FeatureUiModel) -> Unit = {}
) {
    Column(modifier = modifier) {
        Text(
            text = "Chức năng",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(bottom = 10.dp)
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            items.forEach { item ->
                FeatureItem(
                    item = item,
                    modifier = Modifier.weight(1f),
                    onClickItem = {
                        onClickItem(item)
                    }
                )
            }
        }
    }
}