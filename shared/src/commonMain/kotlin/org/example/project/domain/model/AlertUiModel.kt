package org.example.project.domain.model

data class AlertUiModel(
    val title: String,
    val content: String,
    val isHighAlert: Boolean
) {
    companion object {
        fun getDemoList(): List<AlertUiModel> {
            return listOf(
                AlertUiModel(title = "Nộp học phí kỳ 2", content = "Hạn cuối thanh toán học phí là 25/08. Vui lòng hoàn tất để không bị huỷ đăng ký môn học.", isHighAlert = true),
                AlertUiModel(title = "Nộp học phí kỳ 2", content = "Hạn cuối thanh toán học phí là 25/08. Vui lòng hoàn tất để không bị huỷ đăng ký môn học.", isHighAlert = false),
                AlertUiModel(title = "Nộp học phí kỳ 2", content = "Hạn cuối thanh toán học phí là 25/08. Vui lòng hoàn tất để không bị huỷ đăng ký môn học.", isHighAlert = false)
            )
        }
    }
}