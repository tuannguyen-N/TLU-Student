package org.example.project.domain.model

data class NewAndEventUiModel(
    val title: String,
    val time: Long,
    val location: String,
    val isNew: Boolean = true
) {
    companion object {
        fun getDataDemo(): List<NewAndEventUiModel> {
            return listOf(
                NewAndEventUiModel("Demo", 123456789, "Demo"),
                NewAndEventUiModel("Demo", 123456789, "Demo"),
                NewAndEventUiModel("Demo", 123456789, "Demo"),
            )
        }
    }
}