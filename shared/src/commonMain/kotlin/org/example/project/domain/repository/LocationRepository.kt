package org.example.project.domain.repository

import org.example.project.domain.model.LocationData

interface LocationRepository {
    fun getLastKnownLocation(): LocationData
    fun isGpsEnabled(): Boolean
}