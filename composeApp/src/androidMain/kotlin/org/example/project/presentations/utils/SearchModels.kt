package org.example.project.presentations.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import org.example.project.presentations.navigation.AppRoute

/**
 * Một chức năng/màn hình có thể tìm thấy qua thanh search.
 * keywords nên gồm nhiều cách diễn đạt khác nhau (có dấu hoặc không dấu đều
 * được, vì khi so khớp sẽ tự động chuẩn hóa bỏ dấu).
 * icon dùng để hiển thị trong card kết quả - nếu null sẽ fallback về icon Search.
 */
data class SearchableFeature(
    val route: String,
    val title: String,
    val keywords: List<String>,
    val icon: ImageVector? = null
)

object SearchIndex {
    val features: List<SearchableFeature> = listOf(
        SearchableFeature(
            route = AppRoute.Profile,
            title = "Hồ sơ cá nhân",
            keywords = listOf("hồ sơ", "thông tin sinh viên", "profile", "thông tin cá nhân"),
            icon = Icons.Default.Person
        ),
        SearchableFeature(
            route = AppRoute.EditProfile,
            title = "Chỉnh sửa hồ sơ",
            keywords = listOf("chỉnh sửa hồ sơ", "sửa thông tin", "cập nhật thông tin cá nhân"),
            icon = Icons.Default.Edit
        ),
        SearchableFeature(
            route = AppRoute.Notification,
            title = "Thông báo",
            keywords = listOf("thông báo", "tin báo", "notification"),
            icon = Icons.Default.Notifications
        ),
        SearchableFeature(
            route = AppRoute.Setting,
            title = "Cài đặt",
            keywords = listOf("cài đặt", "thiết lập", "setting", "tùy chọn"),
            icon = Icons.Default.Settings
        ),
        SearchableFeature(
            route = AppRoute.TranscriptTerm,
            title = "Bảng điểm theo học kỳ",
            keywords = listOf("bảng điểm", "kết quả học tập", "điểm số", "điểm các môn", "transcript"),
            icon = Icons.Default.List
        ),
        SearchableFeature(
            route = AppRoute.TimetableScreen,
            title = "Thời khóa biểu",
            keywords = listOf("thời khóa biểu", "lịch học", "lịch học tập", "tkb", "schedule"),
            icon = Icons.Default.DateRange
        ),
        SearchableFeature(
            route = AppRoute.OfflineTimetableScreen,
            title = "Thời khóa biểu (offline)",
            keywords = listOf("thời khóa biểu offline", "lịch học ngoại tuyến", "tkb offline"),
            icon = Icons.Default.DateRange
        ),
        SearchableFeature(
            route = AppRoute.Feedback,
            title = "Phản hồi / Góp ý",
            keywords = listOf("phản hồi", "góp ý", "feedback", "ý kiến"),
            icon = Icons.Default.Star
        ),
        SearchableFeature(
            route = AppRoute.FeaturesScreen,
            title = "Tất cả chức năng",
            keywords = listOf("tất cả chức năng", "danh sách chức năng", "menu", "chức năng"),
            icon = Icons.Default.Menu
        ),
        SearchableFeature(
            route = AppRoute.ExamSchedule,
            title = "Lịch thi",
            keywords = listOf("lịch thi", "thi cử", "kế hoạch thi", "exam"),
            icon = Icons.Default.DateRange
        ),
        SearchableFeature(
            route = AppRoute.GpaPredict,
            title = "Dự đoán điểm GPA",
            keywords = listOf("dự đoán điểm", "dự đoán gpa", "tính điểm trung bình dự kiến"),
            icon = Icons.Default.Info
        ),
        SearchableFeature(
            route = AppRoute.GpaTracker,
            title = "Theo dõi điểm trung bình",
            keywords = listOf("theo dõi điểm", "gpa", "điểm trung bình", "gpa tracker"),
            icon = Icons.Default.List
        ),
        SearchableFeature(
            route = AppRoute.DigitalStudentCard,
            title = "Thẻ sinh viên điện tử",
            keywords = listOf("thẻ sinh viên", "thẻ sinh viên điện tử", "student card"),
            icon = Icons.Default.AccountCircle
        ),
        SearchableFeature(
            route = AppRoute.StudentClass,
            title = "Lớp học của tôi",
            keywords = listOf("lớp học", "danh sách lớp", "lớp của tôi", "class"),
            icon = Icons.Default.List
        ),
        SearchableFeature(
            route = AppRoute.ClassSignUp,
            title = "Đăng ký lớp học phần",
            keywords = listOf("đăng ký lớp", "đăng ký môn học", "ghi danh", "đăng ký tín chỉ", "đăng ký học phần"),
            icon = Icons.Default.Add
        ),
        SearchableFeature(
            route = AppRoute.tuitionPayment(),
            title = "Đóng học phí",
            keywords = listOf("học phí", "đóng học phí", "thanh toán học phí", "tuition"),
            icon = Icons.Default.ShoppingCart
        ),
        SearchableFeature(
            route = AppRoute.NewsScreen,
            title = "Tin tức",
            keywords = listOf("tin tức", "thông tin trường", "news", "bản tin"),
            icon = Icons.Default.Info
        ),
        SearchableFeature(
            route = AppRoute.Chat,
            title = "Trò chuyện",
            keywords = listOf("trò chuyện", "tin nhắn", "chat", "nhắn tin"),
            icon = Icons.AutoMirrored.Filled.Send
        ),
        SearchableFeature(
            route = AppRoute.Application,
            title = "Đơn từ / Yêu cầu",
            keywords = listOf("đơn từ", "đơn xin", "yêu cầu hành chính", "application"),
            icon = Icons.Default.Edit
        ),
        SearchableFeature(
            route = AppRoute.AttendanceChecking,
            title = "Điểm danh",
            keywords = listOf("điểm danh", "kiểm tra điểm danh", "attendance"),
            icon = Icons.Default.CheckCircle
        ),
        SearchableFeature(
            route = AppRoute.StudentSearch,
            title = "Tìm kiếm sinh viên",
            keywords = listOf("tìm sinh viên", "tra cứu sinh viên", "danh sách sinh viên"),
            icon = Icons.Default.Search
        )
    )
}