package org.example.project.presentations.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.AssignmentInd
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Class
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material.icons.outlined.Pending
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.QrCode
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material.icons.outlined.Work
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import org.example.project.R
import org.example.project.domain.model.FeatureType
import org.example.project.domain.model.Notification
import org.example.project.domain.model.NotificationType
import org.example.project.domain.model.PaymentType
import org.example.project.presentations.navigation.AppRoute

fun FeatureType.toImageVector(): ImageVector {
    return when (this) {
        FeatureType.DIGITAL_STUDENT_CARD -> Icons.Outlined.AssignmentInd
        FeatureType.EXAM_SCHEDULE -> Icons.Outlined.CalendarMonth
        FeatureType.COURSE_REGISTER -> Icons.Outlined.EditNote
        FeatureType.GPA_PREDICTION -> Icons.Outlined.AutoAwesome
        FeatureType.STUDENT_CLASS -> Icons.Outlined.Class
        FeatureType.MAP -> Icons.Outlined.Map
        FeatureType.CLUB -> Icons.Outlined.Favorite
        FeatureType.JOBS -> Icons.Outlined.Work
        FeatureType.DIGITAL_FORM -> Icons.Outlined.Email
        FeatureType.TUITION_PAYMENT -> Icons.Outlined.Payment
        FeatureType.STUDENT_CARD -> Icons.Outlined.Badge
        FeatureType.FEEDBACK -> Icons.Outlined.Feedback
        FeatureType.TRAINING_OFFICE -> Icons.Outlined.Phone
        FeatureType.ACADEMIC_ADVISOR -> Icons.Outlined.SupportAgent
        FeatureType.UPCOMING -> Icons.Outlined.Pending
    }
}

fun FeatureType.toIconBackgroundColor(): Color {
    return when (this) {
        FeatureType.DIGITAL_STUDENT_CARD -> Color(0xFFDDE8FF)
        FeatureType.EXAM_SCHEDULE -> Color(0xFFD6F5EC)
        FeatureType.COURSE_REGISTER -> Color(0xFFEFE0FF)
        FeatureType.GPA_PREDICTION -> Color(0xFFFFEDD5)
        FeatureType.STUDENT_CLASS -> Color(0xFFFFD6E7)
        FeatureType.MAP -> Color(0xFFD6F5EC)
        FeatureType.CLUB -> Color(0xFFFFE0EC)
        FeatureType.JOBS -> Color(0xFFDCEEFF)
        FeatureType.DIGITAL_FORM -> Color(0xFFE0E8FF)
        FeatureType.TUITION_PAYMENT -> Color(0xFFFFDDD5)
        FeatureType.STUDENT_CARD -> Color(0xFFFFF8D0)
        FeatureType.FEEDBACK -> Color(0xFFE8E8E8)
        FeatureType.TRAINING_OFFICE -> Color(0xFFE8E8E8)
        FeatureType.ACADEMIC_ADVISOR -> Color(0xFFE8E8E8)
        FeatureType.UPCOMING -> Color(0xFFE8E8E8)
    }
}

fun FeatureType.toIconTintColor(): Color {
    return when (this) {
        FeatureType.DIGITAL_STUDENT_CARD -> Color(0xFF3D72E8)
        FeatureType.EXAM_SCHEDULE -> Color(0xFF1AAF7A)
        FeatureType.COURSE_REGISTER -> Color(0xFF9B4DCA)
        FeatureType.GPA_PREDICTION -> Color(0xFFE87D3D)
        FeatureType.STUDENT_CLASS -> Color(0xFFE8437A)
        FeatureType.MAP -> Color(0xFF1AAF7A)
        FeatureType.CLUB -> Color(0xFFE84368)
        FeatureType.JOBS -> Color(0xFF3D88E8)
        FeatureType.DIGITAL_FORM -> Color(0xFF5B7AE8)
        FeatureType.TUITION_PAYMENT -> Color(0xFFE85A3D)
        FeatureType.STUDENT_CARD -> Color(0xFFB8960C)
        FeatureType.FEEDBACK -> Color(0xFF757575)
        FeatureType.TRAINING_OFFICE -> Color(0xFF757575)
        FeatureType.ACADEMIC_ADVISOR -> Color(0xFF757575)
        FeatureType.UPCOMING -> Color(0xFF757575)
    }
}

fun FeatureType.toRoute(): String {
    return when (this) {
        FeatureType.FEEDBACK -> AppRoute.Feedback
        FeatureType.EXAM_SCHEDULE -> AppRoute.ExamSchedule
        FeatureType.GPA_PREDICTION -> AppRoute.GpaPredict
        FeatureType.DIGITAL_STUDENT_CARD -> AppRoute.DigitalStudentCard
        FeatureType.STUDENT_CLASS -> AppRoute.StudentClass
        FeatureType.COURSE_REGISTER -> AppRoute.ClassSignUp
        FeatureType.TUITION_PAYMENT -> AppRoute.TuitionPayment
        else -> "" // TODO:
    }
}

fun Notification.avatarRes(): Int {
    return when (this.type) {
        NotificationType.SCHOOL -> R.drawable.icon_avatar_school
        else -> R.drawable.ic_launcher_background
    }
}

fun Notification.iconRes(): Int {
    return when (this.type) {
        NotificationType.SCHOOL -> R.drawable.icon_school_notification
        NotificationType.DEPARTMENT -> R.drawable.icon_deparment_notification
        else -> R.drawable.icon_teacher_notification
    }
}


fun PaymentType.icon(): ImageVector = when (this) {
    PaymentType.QR_BANK -> Icons.Outlined.QrCode
    PaymentType.QR_BANK_DEMO -> Icons.Outlined.AccountBalance
}