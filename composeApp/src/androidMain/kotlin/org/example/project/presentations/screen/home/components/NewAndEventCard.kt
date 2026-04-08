package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.example.project.R
import org.example.project.domain.model.EventAndNewUiModel
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun NewAndEventCard(
    item: EventAndNewUiModel,
    onOpenNews: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(220.dp)
            .clip(RoundedCornerShape(14.dp))
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(14.dp))
            .background(LocalExtendedColors.current.white)
            .clickable(onClick = { onOpenNews(item.newsUrl) })
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                if (item.isNew) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(LocalExtendedColors.current.red)
                            .padding(horizontal = 12.dp, vertical = 2.dp)
                            .align(Alignment.TopEnd),
                    ) {
                        Text(
                            text = "MỚI",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = LocalExtendedColors.current.white,
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_clock_small),
                        contentDescription = "clock",
                        tint = LocalExtendedColors.current.gray
                    )

                    Text(
                        text = item.timeAgo,
                        style = MaterialTheme.typography.bodySmall,
                        color = LocalExtendedColors.current.gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}