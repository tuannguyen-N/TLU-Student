package org.example.project.domain.model

sealed class ExportState {
    data object Idle : ExportState()
    data object Loading : ExportState()
    data class Success(val file: ExportedFile) : ExportState()
    data class Error(val message: String) : ExportState()
}