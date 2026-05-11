package org.example.project.domain.model

data class AlertActionItem(
    val tag: String,
    val priority: AlertPriority,
    val deadline: String,
    val title: String,
    val description: String,
    val actionLabel: String,
    val referenceType: NotificationReferenceType
)