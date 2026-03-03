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

@Preview
@Composable
fun StudentInformation(
    modifier: Modifier = Modifier,
    onEditProfile: () -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Thông Tin Sinh Viên",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyMedium
            )

            IconButton(onClick = onEditProfile, modifier = Modifier.size(28.dp)) {
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
            InformationItem(R.drawable.icon_student_code, "Mã sinh viên", "A45044")
            InformationItem(R.drawable.icon_name, "Họ và tên", "Nguyễn Văn Vận")
            InformationItem(R.drawable.icon_sex, "Giới tính", "Nam")
            InformationItem(R.drawable.icon_cmnd, "CMND/CCCD", "123456789")
            InformationItem(R.drawable.icon_student_class, "Lớp Sinh Viên", "ABCABC")
            InformationItem(R.drawable.icon_phone_number, "Số điện thoại", "0123456789")
            InformationItem(R.drawable.icon_course, "Khoá học", "Khoá 36")
            AcademicAdvisorItem(
                R.drawable.icon_academic_advisor,
                "Cố vấn học tập",
                "Nguyễn Var Tày",
                "123123"
            )
            InformationItem(R.drawable.icon_mail, "Email", "john.blair@example-pet-store.com")
            InformationItem(R.drawable.icon_ethnic, "Dân tộc", "Kinh")
            InformationItem(R.drawable.icon_adress, "Địa chỉ", "Hà Nội", true)
        }
    }
}