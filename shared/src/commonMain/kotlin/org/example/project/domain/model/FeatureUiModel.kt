package org.example.project.domain.model

data class FeatureUiModel(
    val name: String,
    val type: FeatureType
) {
    companion object {
        fun getDemoList(): List<FeatureUiModel> {
            return listOf(
                FeatureUiModel("upcoming", FeatureType.UPCOMING),
                FeatureUiModel("upasdfasdfcoming", FeatureType.UPCOMING),
                FeatureUiModel("upcoming", FeatureType.UPCOMING),
                FeatureUiModel("upcoming", FeatureType.UPCOMING)
            )
        }
    }
}

enum class FeatureType {
    UPCOMING,
    FEEDBACK
}
