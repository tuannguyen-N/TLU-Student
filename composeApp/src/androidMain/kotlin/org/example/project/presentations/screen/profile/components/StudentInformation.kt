package org.example.project.presentations.screen.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.presentations.screen.profile.components.AcademicAdvisorItem
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun PersonalInformation(
    studentCode: String,
    fullName: String,
    gender: String,
    cardNumber: String,
    phoneNumber: String,
    email: String,
    address: String,
    modifier: Modifier = Modifier,
    onEditProfile: () -> Unit = {}
) {
    Column(modifier = modifier) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Thông tin cá nhân",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyMedium
            )

            IconButton(
                onClick = onEditProfile,
                modifier = Modifier.size(28.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_edit_profile),
                    contentDescription = "edit_profile"
                )
            }
        }

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(13.dp))
                .background(LocalExtendedColors.current.white)
                .padding(horizontal = 15.dp)
        ) {

            InformationItem(R.drawable.icon_student_code, "Mã sinh viên", studentCode)
            InformationItem(R.drawable.icon_name, "Họ và tên", fullName)
            InformationItem(R.drawable.icon_sex, "Giới tính", gender)
            InformationItem(R.drawable.icon_cmnd, "CMND/CCCD", cardNumber)
            InformationItem(R.drawable.icon_phone_number, "Số điện thoại", phoneNumber)
            InformationItem(R.drawable.icon_mail, "Email", email)
            InformationItem(R.drawable.icon_adress, "Địa chỉ", address, true)
        }
    }
}
