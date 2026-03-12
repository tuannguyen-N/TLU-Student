package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.FeatureUiModel
import org.example.project.presentations.components.shimmerBrush
import org.example.project.presentations.screen.features.components.FeatureGrid
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun FeatureList(
    modifier: Modifier = Modifier,
    items: List<FeatureUiModel>,
    onClickItem: (FeatureUiModel) -> Unit = {},
    onClickAll: () -> Unit = {}
) {
    Column(modifier = modifier) {
        Row(
            modifier = modifier
                .padding(horizontal = 15.dp)
                .padding(bottom = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Chức năng",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Tất cả",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = LocalExtendedColors.current.fontBlue,
                modifier = Modifier.clickable(
                    onClick = onClickAll
                )
            )
        }

        FeatureGrid(
            items = items,
            onClickFeature = onClickItem
        )
    }
}