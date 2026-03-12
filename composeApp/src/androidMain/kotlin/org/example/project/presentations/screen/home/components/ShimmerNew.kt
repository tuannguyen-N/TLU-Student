package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.presentations.components.shimmerBrush

@Composable
fun ShimmerNew(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .width(220.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .background(shimmerBrush())
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(14.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(shimmerBrush())
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(14.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(shimmerBrush())
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(12.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(shimmerBrush())
            )
        }
    }
}