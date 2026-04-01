package org.example.project.domain.model

data class FeatureUiModel(
    val name: String,
    val type: FeatureType
) {
    companion object {
        fun getQuickAccessList() = listOf(
            FeatureUiModel("Thẻ sinh viên",       FeatureType.DIGITAL_STUDENT_CARD),
            FeatureUiModel("Lịch thi",        FeatureType.EXAM_SCHEDULE),
            FeatureUiModel("Đăng ký môn",     FeatureType.COURSE_REGISTER),
            FeatureUiModel("Dự đoán GPA",        FeatureType.GPA_PREDICTION),
        )

        fun getGeneralList() = listOf(
            FeatureUiModel("Thẻ sinh viên",        FeatureType.DIGITAL_STUDENT_CARD),
            FeatureUiModel("Lịch thi",        FeatureType.EXAM_SCHEDULE),
            FeatureUiModel("Đăng ký môn",     FeatureType.COURSE_REGISTER),
            FeatureUiModel("Dự đoán GPA",        FeatureType.GPA_PREDICTION),
            FeatureUiModel("Lớp hành chính",       FeatureType.STUDENT_CLASS),
            FeatureUiModel("Bản đồ",          FeatureType.MAP),
            FeatureUiModel("Câu lạc bộ",      FeatureType.CLUB),
            FeatureUiModel("Việc làm",        FeatureType.JOBS),
            FeatureUiModel("Đơn từ điện tử",  FeatureType.DIGITAL_FORM),
            FeatureUiModel("Học phí",      FeatureType.TUITION_PAYMENT),
        )

        fun getSupportList() = listOf(
            FeatureUiModel("Góp ý",           FeatureType.FEEDBACK),
            FeatureUiModel("Phòng đào tạo",   FeatureType.TRAINING_OFFICE),
            FeatureUiModel("Cố vấn học tập",  FeatureType.ACADEMIC_ADVISOR),
        )
    }
}

enum class FeatureType {
    DIGITAL_STUDENT_CARD,
    EXAM_SCHEDULE,
    COURSE_REGISTER,
    GPA_PREDICTION,
    STUDENT_CLASS,
    MAP,
    CLUB,
    JOBS,
    DIGITAL_FORM,
    TUITION_PAYMENT,
    STUDENT_CARD,
    FEEDBACK,
    TRAINING_OFFICE,
    ACADEMIC_ADVISOR,
    UPCOMING
}