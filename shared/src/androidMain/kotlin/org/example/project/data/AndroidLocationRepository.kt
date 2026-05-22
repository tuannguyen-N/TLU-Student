package org.example.project.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.util.Log
import org.example.project.domain.model.LocationData
import org.example.project.domain.repository.LocationRepository

class AndroidLocationRepository(
    private val context: Context
) : LocationRepository {

    @SuppressLint("MissingPermission")
    override fun getLastKnownLocation(): LocationData {
        return try {
            val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            lm.getProviders(true)
                .mapNotNull { lm.getLastKnownLocation(it) }
                .minByOrNull { it.accuracy }
                ?.let { LocationData(it.latitude, it.longitude) }
                ?: LocationData.DEFAULT
        } catch (e: Exception) {
            Log.e("LocationRepo", "getLastKnownLocation error", e)
            LocationData.DEFAULT
        }
    }

    override fun isGpsEnabled(): Boolean {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}