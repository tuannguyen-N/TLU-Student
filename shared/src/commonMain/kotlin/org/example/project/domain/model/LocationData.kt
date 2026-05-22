package org.example.project.domain.model

data class LocationData(
    val latitude: Double,
    val longitude: Double
) {
    companion object {
        val DEFAULT = LocationData(21.028511, 105.804817)
    }
}