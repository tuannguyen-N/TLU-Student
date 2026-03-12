package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.domain.model.NewAndEventUiModel
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun NewAndEventCard(
    item: NewAndEventUiModel
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                clip = false
            )
            .width(220.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(LocalExtendedColors.current.white)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = "new and event",
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
                    Image(
                        painter = painterResource(R.drawable.icon_clock_small),
                        contentDescription = "clock",
                        colorFilter = ColorFilter.tint(LocalExtendedColors.current.gray)
                    )

                    Text(
                        text = "${item.time} ngày trước",
                        style = MaterialTheme.typography.bodySmall,
                        color = LocalExtendedColors.current.gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}