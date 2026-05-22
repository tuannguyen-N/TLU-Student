package org.example.project.presentations.utils

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log

@Suppress("MissingPermission")
fun getLastKnownLocation(context: Context): Pair<Double, Double> {
    try {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers = locationManager.getProviders(true)
        var bestLocation: Location? = null
        for (provider in providers) {
            val l = locationManager.getLastKnownLocation(provider) ?: continue
            if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                bestLocation = l
            }
        }
        if (bestLocation != null) {
            return Pair(bestLocation.latitude, bestLocation.longitude)
        }
    } catch (e: Exception) {
        Log.e("AndroidLocationProvider", "getLastKnownLocation: $e")
    }
    return Pair(21.028511, 105.804817)
}
