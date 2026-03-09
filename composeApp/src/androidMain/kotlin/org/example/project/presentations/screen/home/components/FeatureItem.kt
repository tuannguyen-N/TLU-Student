package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.FeatureUiModel
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.toDrawable

@Composable
fun FeatureItem(
    modifier: Modifier = Modifier,
    item: FeatureUiModel,
    onClickItem: () -> Unit = {}
) {
    Column(
        modifier = modifier.clickable {

        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            onClick = onClickItem,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
        ) {
            Image(
                painter = painterResource(item.type.toDrawable()),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }

        Text(
            text = item.name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            color = LocalExtendedColors.current.gray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 6.dp)
                .fillMaxWidth()
        )

    }
}