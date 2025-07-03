package com.example.se114_whatthefood_fe.view_model

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.here.sdk.core.GeoCoordinates

class MapViewModel {
    var currentMapLocation by mutableStateOf<Location?>(null)

    fun updateMapLocation(latitude: Double, longitude: Double) {
        currentMapLocation = Location("").apply {
            this.latitude = latitude
            this.longitude = longitude
        }
    }
}