package org.example.project.domain.model

sealed class AppError(open val code: Int, open val messageVi: String) {

    sealed class Common(override val code: Int, override val messageVi: String) :
        AppError(code, messageVi) {
        data object InvalidInput : Common(-1, "Dữ liệu không hợp lệ")
        data object NotFound : Common(-2, "Không tìm thấy dữ liệu")
        data object Unauthorized : Common(-3, "Vui lòng đăng nhập")
        data object Forbidden : Common(-4, "Bạn không có quyền truy cập")
        data object InternalError : Common(-10, "Lỗi hệ thống")
        data object DatabaseViolation : Common(-27, "Lỗi dữ liệu hệ thống")
        data class Unknown(val rawCode: Int) : Common(rawCode, "Lỗi không xác định ($rawCode)")
    }

    sealed class Attendance(override val code: Int, override val messageVi: String) :
        AppError(code, messageVi) {
        data object AlreadyCheckedIn : Attendance(-25, "Bạn đã điểm danh trước đó")
        data object OutsideArea : Attendance(-26, "Bạn nằm ngoài phạm vi cho phép")
        data object InvalidOrExpiredQr : Attendance(-28, "Mã QR không hợp lệ hoặc đã hết hạn")
    }

    sealed class Enrollment(override val code: Int, override val messageVi: String) :
        AppError(code, messageVi) {
        data object RegistrationNotFound : Enrollment(-2, "Chưa có lịch đăng ký học")
        data object ConditionNotMet : Enrollment(-100, "Không đủ điều kiện đăng ký")
        data object ScheduleConflict : Enrollment(-101, "Trùng lịch học")
        data object PreRequisiteNotMet : Enrollment(-102, "Chưa đạt môn tiên quyết là:")
        data object MaxCreditExceeded : Enrollment(-103, "Vượt quá số tín chỉ tối đa")
        data object DuplicateSubject : Enrollment(-104, "Môn học đã được đăng ký")
        data object DuplicateCourseClass : Enrollment(-105, "Lớp học phần đã được đăng ký")
        data object SubjectAlreadyPassed : Enrollment(-106, "Môn học đã hoàn thành")
        data object ClassFull : Enrollment(-107, "Lớp học phần đã đầy")
        data object NotInProgram : Enrollment(-108, "Môn học không thuộc chương trình")
        data object OutsideRegPeriod : Enrollment(-109, "Ngoài thời gian đăng ký")
    }

    companion object {
        fun fromAttendanceCode(code: Int): AppError = when (code) {
            -3 -> Common.Unauthorized
            -25 -> Attendance.AlreadyCheckedIn
            -26 -> Attendance.OutsideArea
            -27 -> Common.DatabaseViolation
            -28 -> Attendance.InvalidOrExpiredQr
            else -> Common.Unknown(code)
        }

        fun fromEnrollmentCode(code: Int): AppError = when (code) {
            -2 -> Enrollment.RegistrationNotFound
            -3 -> Common.Unauthorized
            -4 -> Common.Forbidden
            -27 -> Common.DatabaseViolation
            -100 -> Enrollment.ConditionNotMet
            -101 -> Enrollment.ScheduleConflict
            -102 -> Enrollment.PreRequisiteNotMet
            -103 -> Enrollment.MaxCreditExceeded
            -104 -> Enrollment.DuplicateSubject
            -105 -> Enrollment.DuplicateCourseClass
            -106 -> Enrollment.SubjectAlreadyPassed
            -107 -> Enrollment.ClassFull
            -108 -> Enrollment.NotInProgram
            -109 -> Enrollment.OutsideRegPeriod
            else -> Common.Unknown(code)
        }
    }
}