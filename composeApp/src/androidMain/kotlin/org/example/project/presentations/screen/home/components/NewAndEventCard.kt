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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.domain.model.NewAndEventUiModel
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun NewAndEventCard(
    item: NewAndEventUiModel = NewAndEventUiModel.getDataDemo().first()
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            )
            .width(250.dp)
            .height(140.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "new and event",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        val gradientBrush = remember {
            Brush.verticalGradient(
                colors = listOf(
                    Color.Black.copy(alpha = 0f),
                    Color.Black.copy(alpha = 0.5f),
                    Color.Black.copy(alpha = 0.9f)
                )
            )
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(brush = gradientBrush)
        )

        Box(
            modifier = Modifier
                .padding(7.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(LocalExtendedColors.current.red)
                .padding(horizontal = 8.dp)
                .align(Alignment.TopEnd),
        ) {
            if (item.isNew) {
                Text(
                    text = "Mới",
                    style = MaterialTheme.typography.labelSmall,
                    color = LocalExtendedColors.current.white,
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .align(Alignment.BottomStart)
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 2.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(LocalExtendedColors.current.fontBlue)
                    .padding(horizontal = 8.dp, vertical = 1.dp)
            ) {
                Text(
                    text = "Hội thảo",
                    style = MaterialTheme.typography.labelSmall,
                    color = LocalExtendedColors.current.white,
                )
            }

            Text(
                text = item.title,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = LocalExtendedColors.current.white,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 3.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_clock_small),
                    contentDescription = "clock"
                )

                Text(
                    text = "${item.time} ngày trước",
                    style = MaterialTheme.typography.bodySmall,
                    color = LocalExtendedColors.current.white,
                    modifier = Modifier.padding(start = 2.dp)
                )

                Image(
                    painter = painterResource(R.drawable.icon_location_small),
                    contentDescription = "location",
                    modifier = Modifier.padding(start = 5.dp)
                )

                Text(
                    text = item.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = LocalExtendedColors.current.white,
                    modifier = Modifier.padding(start = 2.dp)
                )
            }
        }
    }
}