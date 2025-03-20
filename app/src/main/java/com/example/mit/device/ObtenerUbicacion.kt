package com.example.mit.device

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


@SuppressLint("MissingPermission")
 fun obtenerUbicacion(context: Context): Pair<Double, Double>? {
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    return try {
        val location: Location? = fusedLocationClient.lastLocation.result
        location?.let {
            Pair(it.latitude, it.longitude)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}