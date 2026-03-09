package org.example.project.domain.model

data class FeatureUiModel(
    val name: String,
    val type: FeatureType
) {
    companion object {
        fun getQuickAccessList() = listOf(
            FeatureUiModel("Xem điểm",       FeatureType.VIEW_GRADES),
            FeatureUiModel("Lịch học",        FeatureType.SCHEDULE),
            FeatureUiModel("Đăng ký môn",     FeatureType.COURSE_REGISTER),
            FeatureUiModel("Tài liệu",        FeatureType.DOCUMENTS),
        )

        fun getGeneralList() = listOf(
            FeatureUiModel("Xem điểm",        FeatureType.VIEW_GRADES),
            FeatureUiModel("Lịch học",        FeatureType.SCHEDULE),
            FeatureUiModel("Đăng ký môn",     FeatureType.COURSE_REGISTER),
            FeatureUiModel("Tài liệu",        FeatureType.DOCUMENTS),
            FeatureUiModel("Ký túc xá",       FeatureType.DORMITORY),
            FeatureUiModel("Bản đồ",          FeatureType.MAP),
            FeatureUiModel("Câu lạc bộ",      FeatureType.CLUB),
            FeatureUiModel("Việc làm",        FeatureType.JOBS),
            FeatureUiModel("Đơn từ điện tử",  FeatureType.DIGITAL_FORM),
            FeatureUiModel("Thanh toán",      FeatureType.PAYMENT),
        )

        fun getSupportList() = listOf(
            FeatureUiModel("Góp ý",           FeatureType.FEEDBACK),
            FeatureUiModel("Phòng đào tạo",   FeatureType.TRAINING_OFFICE),
            FeatureUiModel("Cố vấn học tập",  FeatureType.ACADEMIC_ADVISOR),
        )
    }
}

enum class FeatureType {
    VIEW_GRADES,
    SCHEDULE,
    COURSE_REGISTER,
    DOCUMENTS,
    DORMITORY,
    MAP,
    CLUB,
    JOBS,
    DIGITAL_FORM,
    PAYMENT,
    STUDENT_CARD,
    FEEDBACK,
    TRAINING_OFFICE,
    ACADEMIC_ADVISOR,
    UPCOMING
}