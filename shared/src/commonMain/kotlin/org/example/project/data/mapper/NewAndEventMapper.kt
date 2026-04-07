package org.example.project.data.mapper

import kotlinx.datetime.LocalDate
import org.example.project.data.remote.dto.news.EventOrNew
import org.example.project.domain.model.EventAndNewUiModel

fun List<EventOrNew>.toListUiModel(): List<EventAndNewUiModel> {
    return map { it.toUiModel() }
}

fun EventOrNew.toUiModel(): EventAndNewUiModel {
    return EventAndNewUiModel(
        excerpt = excerpt,
        imageUrl = imageUrl,
        newsUrl = newsUrl,
        publishDate = publishDate,
        title = title,
        source = source,
        isNew = publishDate.isNew(),
        timeAgo = publishDate.timeAgo()
    )
}

fun String.timeAgo(): String {
    val publishDate = LocalDate.parse(this)
    val diffDays = today.toEpochDays() - publishDate.toEpochDays()

    return when {
        diffDays == 0L -> "Hôm nay"
        diffDays == 1L -> "Hôm qua"
        diffDays < 30 -> "$diffDays ngày trước"
        diffDays < 365 -> "${diffDays / 30} tháng trước"
        else -> "${diffDays / 365} năm trước"
    }
}

private fun String.isNew(): Boolean {
    val publishDate = LocalDate.parse(this)
    val diffDays = today.toEpochDays() - publishDate.toEpochDays()
    return diffDays <= 3
}