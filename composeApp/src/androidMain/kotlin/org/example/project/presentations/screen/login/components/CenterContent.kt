package org.example.project.presentations.screen.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun CenterContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.tlu_logo),
            contentDescription = null,
            modifier = Modifier
                .height(50.dp)
                .padding(bottom = 10.dp)
        )

        Text(
            text = "CỔNG THÔNG TIN ĐÀO TẠO",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = LocalExtendedColors.current.mainBlue
        )
    }
}