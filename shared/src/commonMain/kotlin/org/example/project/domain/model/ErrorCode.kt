package org.example.project.domain.model

enum class ErrorCode(
    val code: Int,
    val messageVi: String
) {
    INVALID_INPUT(-1, "Dữ liệu không hợp lệ"),
    NOT_FOUND(-2, "Không tìm thấy dữ liệu"),
    UNAUTHORIZED(-3, "Vui lòng đăng nhập"),
    FORBIDDEN(-4, "Bạn không có quyền truy cập"),
    VALIDATION_ERROR(-5, "Dữ liệu xác thực không hợp lệ"),
    INTERNAL_ERROR(-10, "Lỗi hệ thống"),
    EXTERNAL_ERROR(-13, "Lỗi dịch vụ bên ngoài"),
    CONFLICT(-25, "Dữ liệu đã tồn tại"),
    BAD_REQUEST(-26, "Yêu cầu không hợp lệ"),
    DATABASE_VIOLATION(-27, "Lỗi dữ liệu hệ thống"),

    ENROLLMENT_CONDITION_NOT_MET(-100, "Không đủ điều kiện đăng ký"),
    SCHEDULE_CONFLICT(-101, "Trùng lịch học"),
    PRE_REQUISITE_NOT_MET(-102, "Chưa đạt môn tiên quyết"),
    MAX_CREDIT_EXCEEDED(-103, "Vượt quá số tín chỉ tối đa"),
    DUPLICATE_SUBJECT(-104, "Môn học đã được đăng ký"),
    DUPLICATE_COURSE_CLASS(-105, "Lớp học phần đã được đăng ký"),
    SUBJECT_ALREADY_PASSED(-106, "Môn học đã hoàn thành"),
    CLASS_FULL(-107, "Lớp học phần đã đầy"),
    SUBJECT_NOT_IN_PROGRAM(-108, "Môn học không thuộc chương trình đào tạo"),
    OUTSIDE_REGISTRATION_PERIOD(-109, "Ngoài thời gian đăng ký");

    companion object {
        fun fromCode(code: Int): ErrorCode? {
            return entries.find { it.code == code }
        }
    }
}