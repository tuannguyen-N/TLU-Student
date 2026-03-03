package org.example.project.presentations.screen.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import org.example.project.R
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun ContactPersonInformation(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Thông tin người liên hệ",
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(13.dp))
                .background(LocalExtendedColors.current.white)
                .padding(horizontal = 15.dp)
        ) {
            InformationItem(R.drawable.icon_name, "Họ và tên", "Nguyễn Văn Vận")
            InformationItem(R.drawable.icon_phone_number, "Số điện thoại", "0123456789")
            InformationItem(R.drawable.icon_adress, "Địa chỉ", "Hà Nội", true)
        }
    }
}