package com.drodobyte.coreandroid.x

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

fun GoogleMap.showUser(show: Boolean) {
    isMyLocationEnabled = show
    uiSettings.isMyLocationButtonEnabled = show
}

fun GoogleMap.moveTo(x: Double, y: Double, z: Double, showUser: Boolean = false) {
    showUser(showUser)
    moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(x, y), z.toFloat()))
}

fun GoogleMap.moveToUser(context: Context?) {
    showUser(true)
    LocationServices.getFusedLocationProviderClient(context!!).lastLocation
        .addOnSuccessListener { loc ->
            moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(loc.latitude, loc.longitude), 16f
                )
            )
        }
}
