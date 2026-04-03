package org.example.project.data.mapper

import org.example.project.data.remote.dto.tuition_detail.TuitionDetailData
import org.example.project.domain.model.TuitionDetailUiModel

fun TuitionDetailData.toUiModel(): TuitionDetailUiModel {
    return TuitionDetailUiModel(
        dueDate = dueDate.toDisplayDate(),
        finalAmount = finalAmount.toLong().toFormatAmountAndD(),
        items = items,
        status = status.toTuitionStatus(),
        totalAmount = totalAmount.toLong().toFormatAmountAndD(),
        semester = semesterName
    )
}