package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.local.entity.QuoteEntity
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun QuoteCard(
    modifier: Modifier = Modifier,
    dailyQuote: QuoteEntity?
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = LocalExtendedColors.current.white),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                start = 28.dp,
                end = 28.dp,
                top = 16.dp,
                bottom = 28.dp
            )
        ) {
            Text(
                text = "\u201C",
                fontSize = 60.sp,
                color = LocalExtendedColors.current.mainRed,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                lineHeight = 60.sp,
                modifier = Modifier.offset(x = (-6).dp)
            )

            Text(
                text = "\"${dailyQuote?.quote}\"",
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                lineHeight = 30.sp,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            HorizontalDivider(
                modifier = Modifier.width(100.dp),
                thickness = 1.5.dp,
                color = LocalExtendedColors.current.mainRed
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "— ${dailyQuote?.author}",
                fontSize = 13.sp,
                color = LocalExtendedColors.current.mainRed,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                letterSpacing = 2.sp
            )
        }
    }
}