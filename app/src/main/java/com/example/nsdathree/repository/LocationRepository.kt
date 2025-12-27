package com.example.nsdathree.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng


class LocationRepository(
    private val context: Context
) {
    private val fusedClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun getLastLocation(
        onSuccess: (LatLng) -> Unit,
        onError: () -> Unit
    ) {
        if (
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            onError()
            return
        }

        fusedClient.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    onSuccess(LatLng(it.latitude, it.longitude))
                } ?: onError()
            }
    }
}