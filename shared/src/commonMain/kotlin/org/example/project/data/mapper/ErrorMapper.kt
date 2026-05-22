package org.example.project.data.mapper

import org.example.project.domain.model.AppError

object ErrorMapper {
    fun mapAttendance(code: Int, defaultMessage: String? = null): AppError =
        AppError.fromAttendanceCode(code)

    fun mapEnrollment(code: Int, defaultMessage: String? = null): AppError =
        AppError.fromEnrollmentCode(code)
}