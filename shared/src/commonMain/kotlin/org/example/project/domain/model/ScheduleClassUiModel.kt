package org.example.project.domain.model

data class ScheduleClassUiModel(
    val name: String,
    val room: String,
    val houseLocation: String,
    val isCurrent: Boolean
){
    companion object {
        fun getDataDemo(): List<ScheduleClassUiModel> {
            return listOf(
                ScheduleClassUiModel("Demo", "demo", "demo", true),
                ScheduleClassUiModel("Demo", "demo", "demo", false),
                ScheduleClassUiModel("Demo", "demo", "demo", false),
            )
        }
    }
}
