package org.example.project.presentations.screen.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun InformationItem(
    iconRes: Int = R.drawable.icon_student_code,
    title: String = "Mã sinh viên",
    value: String = "A45044",
    isLastItem: Boolean = false
) {
    Column(

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = null
            )

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 5.dp),
                color = LocalExtendedColors.current.gray
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        if (!isLastItem) {
            Spacer(
                modifier = Modifier.background(LocalExtendedColors.current.gray).fillMaxWidth().height(1.dp)
            )
        }
    }
}