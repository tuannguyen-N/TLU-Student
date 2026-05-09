package org.example.project.data.mapper

import org.example.project.domain.model.ErrorCode

object ErrorMapper {
    fun map(code: Int, defaultMessage: String?): String {
        return ErrorCode.fromCode(code)?.messageVi ?: defaultMessage ?: "Đã xảy ra lỗi"
    }
}