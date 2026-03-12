package org.example.project.presentations.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material.icons.outlined.Pending
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material.icons.outlined.Work
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import org.example.project.R
import org.example.project.domain.model.FeatureType
import org.example.project.domain.model.Notification
import org.example.project.domain.model.NotificationType
import org.example.project.presentations.navigation.Routes

fun FeatureType.toImageVector(): ImageVector {
    return when (this) {
        FeatureType.VIEW_GRADES     -> Icons.Outlined.Grade
        FeatureType.EXAM_SCHEDULE        -> Icons.Outlined.CalendarMonth
        FeatureType.COURSE_REGISTER -> Icons.Outlined.EditNote
        FeatureType.DOCUMENTS       -> Icons.AutoMirrored.Outlined.MenuBook
        FeatureType.DORMITORY       -> Icons.Outlined.Apartment
        FeatureType.MAP             -> Icons.Outlined.Map
        FeatureType.CLUB            -> Icons.Outlined.Favorite
        FeatureType.JOBS            -> Icons.Outlined.Work
        FeatureType.DIGITAL_FORM    -> Icons.Outlined.Email
        FeatureType.PAYMENT         -> Icons.Outlined.Payment
        FeatureType.STUDENT_CARD    -> Icons.Outlined.Badge
        FeatureType.FEEDBACK        -> Icons.Outlined.Feedback
        FeatureType.TRAINING_OFFICE -> Icons.Outlined.Phone
        FeatureType.ACADEMIC_ADVISOR-> Icons.Outlined.SupportAgent
        FeatureType.UPCOMING        -> Icons.Outlined.Pending
    }
}

fun FeatureType.toIconBackgroundColor(): Color {
    return when (this) {
        FeatureType.VIEW_GRADES      -> Color(0xFFDDE8FF)
        FeatureType.EXAM_SCHEDULE         -> Color(0xFFD6F5EC)
        FeatureType.COURSE_REGISTER  -> Color(0xFFEFE0FF)
        FeatureType.DOCUMENTS        -> Color(0xFFFFEDD5)
        FeatureType.DORMITORY        -> Color(0xFFFFD6E7)
        FeatureType.MAP              -> Color(0xFFD6F5EC)
        FeatureType.CLUB             -> Color(0xFFFFE0EC)
        FeatureType.JOBS             -> Color(0xFFDCEEFF)
        FeatureType.DIGITAL_FORM     -> Color(0xFFE0E8FF)
        FeatureType.PAYMENT          -> Color(0xFFFFDDD5)
        FeatureType.STUDENT_CARD     -> Color(0xFFFFF8D0)
        FeatureType.FEEDBACK         -> Color(0xFFE8E8E8)
        FeatureType.TRAINING_OFFICE  -> Color(0xFFE8E8E8)
        FeatureType.ACADEMIC_ADVISOR -> Color(0xFFE8E8E8)
        FeatureType.UPCOMING         -> Color(0xFFE8E8E8)
    }
}

fun FeatureType.toIconTintColor(): Color {
    return when (this) {
        FeatureType.VIEW_GRADES      -> Color(0xFF3D72E8)
        FeatureType.EXAM_SCHEDULE         -> Color(0xFF1AAF7A)
        FeatureType.COURSE_REGISTER  -> Color(0xFF9B4DCA)
        FeatureType.DOCUMENTS        -> Color(0xFFE87D3D)
        FeatureType.DORMITORY        -> Color(0xFFE8437A)
        FeatureType.MAP              -> Color(0xFF1AAF7A)
        FeatureType.CLUB             -> Color(0xFFE84368)
        FeatureType.JOBS             -> Color(0xFF3D88E8)
        FeatureType.DIGITAL_FORM     -> Color(0xFF5B7AE8)
        FeatureType.PAYMENT          -> Color(0xFFE85A3D)
        FeatureType.STUDENT_CARD     -> Color(0xFFB8960C)
        FeatureType.FEEDBACK         -> Color(0xFF757575)
        FeatureType.TRAINING_OFFICE  -> Color(0xFF757575)
        FeatureType.ACADEMIC_ADVISOR -> Color(0xFF757575)
        FeatureType.UPCOMING         -> Color(0xFF757575)
    }
}

fun FeatureType.toRoute(): String{
    return when (this) {
        FeatureType.FEEDBACK -> Routes.Feedback
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